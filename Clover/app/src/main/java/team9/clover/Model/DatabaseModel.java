package team9.clover.Model;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public static void loadProductSlider(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(ProductModel.class.getSimpleName())
                .whereEqualTo("screen", (long) 3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<ProductModel> productModelList = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                productModelList.add(ProductModel.castFromFirestore(snapshot));
                            }

                            homePageModelList.add(new HomePageModel(HomePageModel.SLIDER_PRODUCT_VIEW_TYPE, R.drawable.cart_check, "Bán nhiều nhất", productModelList));
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

                            // adapter báo cho RecyclerView => cập nhật giao diện
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
    * Load ảnh carousel lên homepage
    * */
    public static void loadHomePage(HomePageAdapter adapter, Activity activity) {
        if (firebaseFirestore == null) firebaseFirestore = FirebaseFirestore.getInstance();
        loadCarousel(adapter, activity);
    }

    public static void loadCarousel(HomePageAdapter adapter, Activity activity) {
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

    public static void loadBanner(HomePageAdapter adapter, Activity activity) {
        firebaseFirestore.collection(BannerModel.class.getSimpleName())
                .document("0")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    BannerModel bannerModel = task.getResult().toObject(BannerModel.class);
                    homePageModelList.add(new HomePageModel(bannerModel));
                    loadProductSlider(adapter, activity);
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
}