package team9.clover.Model;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team9.clover.ErrorActivity;
import team9.clover.Adapter.CategoryAdapter;
import team9.clover.Adapter.HomePageAdapter;
import team9.clover.Module.Reuse;
import team9.clover.R;

public class DatabaseModel {

    public static FirebaseUser USER = null;
    public static FirebaseFirestore firebaseFirestore = null;
    public static FirebaseAuth firebaseAuth = null;

    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModel> homePageModelList = new ArrayList<>();


    /*
     * Load người dùng hiện tại trên thiết bị
     * */
    public static void getCurrentUser() {
        USER = FirebaseAuth.getInstance().getCurrentUser();
    }

    /*
     * Thêm thông tin new user vào Firestore với key là email
     * */
    public static Task addNewUser(String email, UserModel newUser) {
        if (firebaseFirestore == null) firebaseFirestore = FirebaseFirestore.getInstance();
        return firebaseFirestore.collection(UserModel.class.getSimpleName()).document(email).set(newUser);
    }

    /*
     * Đăng kí new user này với Firebase authentication
     * */
    public static Task signUp(String email, String password) {
        if (firebaseAuth == null) firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    /*
     * Đăng nhập bằng Firebase authentication
     * */
    public static Task signIn(String email, String password) {
        if (firebaseAuth == null) firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    /*
     * Dùng để gửi email reset password cho user
     * */
    public static Task resetPassword(String email) {
        if (firebaseAuth == null) firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.sendPasswordResetEmail(email);
    }

    /*
    * Load icon, title trên thanh category toolbar
    * */
    public static void loadCategory(CategoryAdapter adapter, Activity activity) {
        if (firebaseFirestore == null) firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(CategoryModel.class.getSimpleName())
                .orderBy("index")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                categoryModelList.add(snapshot.toObject(CategoryModel.class));
                            }

                            if (adapter != null) adapter.notifyDataSetChanged();
                        } else {
                            if (activity != null) {
                                activity.finish();
                                activity.startActivity(new Intent(activity, ErrorActivity.class));
                                Reuse.startActivity(activity);
                            }
                        }
                    }
                });
    }

    /*
    * Load ảnh các item cho màn hình Home Fragment
    * */
    public static void loadHomePage(HomePageAdapter adapter, Activity activity) {
        if (firebaseFirestore == null) firebaseFirestore = FirebaseFirestore.getInstance();
        loadCarousel(adapter, activity);
//        loadHomePageProduct(adapter, activity);
    }

    /*
     * Load các sản phẫm thuộc mục sản phẩm mới ở Hame Fragment
     * */
    private static void loadHomePageProduct(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(ProductModel.class.getSimpleName())
                .whereGreaterThan("screen", (long) HomePageModel.BANNER_VIEW_TYPE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductModel> newProducts = new ArrayList<>();
                            List<ProductModel> sliderProducts = new ArrayList<>();
                            List<ProductModel> gridProducts = new ArrayList<>();

                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                ProductModel productModel = snapshot.toObject(ProductModel.class);
                                if (productModel.getScreen() == HomePageModel.NEW_PRODUCT_VIEW_TYPE) {
                                    newProducts.add(productModel);
                                } else if (productModel.getScreen() == HomePageModel.SLIDER_PRODUCT_VIEW_TYPE) {
                                    sliderProducts.add(productModel);
                                } else if (productModel.getScreen() == HomePageModel.GRID_PRODUCT_VIEW_TYPE) {
                                    gridProducts.add(productModel);
                                }
                            }

                            homePageModelList.add(new HomePageModel(HomePageModel.NEW_PRODUCT_VIEW_TYPE, -1, null, newProducts));
                            homePageModelList.add(new HomePageModel(HomePageModel.SLIDER_PRODUCT_VIEW_TYPE, R.drawable.icon_cart_check, "Bán nhiều nhất", sliderProducts));
                            homePageModelList.add(new HomePageModel(HomePageModel.GRID_PRODUCT_VIEW_TYPE, R.drawable.icon_discount, "Khuyến mãi hôm nay", gridProducts));
                            homePageModelList.add(new HomePageModel(HomePageModel.FOOTER_VIEW_TYPE));
                            if (adapter != null) adapter.notifyDataSetChanged();
                        } else {
                            if (activity != null) {
                                activity.finish();
                                activity.startActivity(new Intent(activity, ErrorActivity.class));
                                Reuse.startActivity(activity);
                            }
                        }
                    }
                });
    }

    /*
    * Load phần Carousel cho Home Fragment
    * */
    private static void loadCarousel(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(CarouselModel.class.getSimpleName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CarouselModel> carouselModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                carouselModelList.add(snapshot.toObject(CarouselModel.class));
                            }

                            homePageModelList.add(new HomePageModel(carouselModelList));
                            loadBanner(adapter, activity);
                        } else {
                            activity.finish();
                            activity.startActivity(new Intent(activity, ErrorActivity.class));
                            Reuse.startActivity(activity);
                        }
                    }
                });
    }

    /*
    * Load bannner quảng cáo ở Home Fragment
    * */
    private static void loadBanner(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(BannerModel.class.getSimpleName())
                .document("0")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    BannerModel bannerModel = task.getResult().toObject(BannerModel.class);
                    homePageModelList.add(new HomePageModel(bannerModel));
                    loadHomePageProduct(adapter, activity);
                } else {
                    activity.finish();
                    activity.startActivity(new Intent(activity, ErrorActivity.class));
                    Reuse.startActivity(activity);
                }
            }
        });
    }

    /*
    * Load các sản phẩm thuộc mục Bán nhiều nhất ở Home Fragment
    * */
    private static void loadSliderProduct(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(ProductModel.class.getSimpleName())
                .whereLessThanOrEqualTo("screen", (long) HomePageModel.GRID_PRODUCT_VIEW_TYPE)
                .orderBy("screen")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductModel> productModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                productModelList.add(snapshot.toObject(ProductModel.class));
                            }

                            homePageModelList.add(new HomePageModel(HomePageModel.SLIDER_PRODUCT_VIEW_TYPE, R.drawable.icon_cart_check, "Bán nhiều nhất", productModelList));
                            loadGridProduct(adapter, activity);
                        } else {
                            activity.finish();
                            activity.startActivity(new Intent(activity, ErrorActivity.class));
                            Reuse.startActivity(activity);
                        }
                    }
                });
    }

    /*
    * Load các sản phẩm thuộc mục Khuyến mãi hôm nay ở Home Fragment
    * */
    private static void loadGridProduct(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(ProductModel.class.getSimpleName())
                .whereEqualTo("screen", (long) HomePageModel.GRID_PRODUCT_VIEW_TYPE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductModel> productModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                productModelList.add(snapshot.toObject(ProductModel.class));
                            }

                            homePageModelList.add(new HomePageModel(HomePageModel.GRID_PRODUCT_VIEW_TYPE, R.drawable.icon_discount, "Khuyến mãi hôm nay", productModelList));
                            adapter.notifyDataSetChanged();
                        } else {
                            activity.finish();
                            activity.startActivity(new Intent(activity, ErrorActivity.class));
                            Reuse.startActivity(activity);
                        }
                    }
                });
    }

    /*
     * Đăng xuất khỏi current user
     * */
    public static void signOut() {
        if (USER != null) firebaseAuth.signOut();
    }

    //______________________________________________________________________________________________________ DANGER FUNCTION




    public static void addProduct() {
        int folderStart = 20; // index của sản [hẩm bắt đầu ghi
        long screen = 2; // các sản phẩm này sẽ hiễn thị màn hình nào


        ArrayList<Long> categorys = new ArrayList<>();
        categorys.add((long) 1);
        categorys.add((long) 1);
        categorys.add((long) 1);
        categorys.add((long) 1);
        categorys.add((long) 1);
        categorys.add((long) 1);

        ArrayList<Integer> noImages = new ArrayList<>();
        noImages.add(5);
        noImages.add(4);
        noImages.add(3);
        noImages.add(4);
        noImages.add(4);
        noImages.add(4);

        ArrayList<String> titles = new ArrayList<>();
        titles.add("Nàng thơ");
        titles.add("Xuận hạ thu đông");
        titles.add("Nắng");
        titles.add("Rạng đông");
        titles.add("Nhật thực");
        titles.add("Bếp lửa");


        /* start */
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        String price = "550.000 đ",
                cutPrice = "820.000 đ",
                description = "Tiến Dũng là trụ cột ở hàng thủ của Việt Nam, thường xuyên đá cặp cùng Quế Ngọc Hải và Đỗ Duy Mạnh trong sơ đồ ba trung vệ. Anh được HLV Park triệu tập ở mọi giải đấu, góp phần quan trọng làm nên chiến tích ở U23 châu Á 2018, vô địch AFF Cup 2018 và vào vòng loại thứ ba World Cup 2022 - khu vực châu Á.";
        ArrayList<String> size = new ArrayList<>(Arrays.asList("XS", "S", "L"));
        ArrayList<String> bodyName = new ArrayList<>(Arrays.asList("Vai", "Lưng", "Dài áo"));
        ArrayList<Long> measure = new ArrayList<>();
        measure.add((long) 1);
        measure.add((long) 1);
        measure.add((long) 1);
        measure.add((long) 1);
        measure.add((long) 1);
        measure.add((long) 1);
        measure.add((long) 1);
        measure.add((long) 1);
        measure.add((long) 1);


        ArrayList<String> info = new ArrayList<>();
        info.add("Chất liêu: vài flannel");
        info.add("Chất vải mềm mại, không quá dày");
        info.add("Chi tiết trước & sau thêu KTS sắc nét");
        info.add("Mạc vải đỏ đô được may bên hông túi áo");
        info.add("Zipper HKK 2 chiều");
        /* end*/

        for (int i = 0; i < titles.size(); ++i) {
            int noImage = noImages.get(i);
            long category = categorys.get(i);
            String title = titles.get(i);
            ArrayList<String> image = new ArrayList<>();
            ProductModel productModel = new ProductModel(screen, category, title, price, cutPrice, description, image, size, bodyName, measure, info);
            addDataRecursion(firebaseStorage, productModel, folderStart + i, 0, noImage, Integer.toString(folderStart + i));
        }
    }

    private static void addDataRecursion(FirebaseStorage firebaseStorage, ProductModel product, final int folder, int step, final int noImage, final String documentId) {
        if (step == noImage) {
            firebaseFirestore.collection(ProductModel.class.getSimpleName())
                    .document(documentId)
                    .set(product);
        } else if (step < noImage) {
            firebaseStorage.getReference(ProductModel.FIRESTORAGE + "/" + folder + "/" + step + ".jpg")
                    .getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        String image = task.getResult().toString();
                        product.addImage(image);
                        addDataRecursion(firebaseStorage, product, folder, step + 1, noImage, documentId);
                    }
                }
            });
        }
    }
}