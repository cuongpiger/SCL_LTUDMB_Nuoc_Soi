package team9.clover.Module;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import team9.clover.Model.Category;
import team9.clover.Model.HomePage;
import team9.clover.Model.HorizontalProductScroll;
import team9.clover.Model.Slider;

public class DBqueries {
    public static  FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<Category> categoryList = new ArrayList<>();;
    public static List<HomePage> homePageList = new ArrayList<>();


    public static void loadCategories(CategoryAdapter adapter, Context context) {
        firebaseFirestore.collection("Category")
                .orderBy("index")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // duyệt qua Firebase Storage => lấy về => lưu vào mảng
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                categoryList.add(new Category(snapshot.get("icon").toString(), snapshot.get("name").toString()));
                            }

                            // adapter báo cho RecyclerView => cập nhật giao diện
                            adapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadFragmentData(HomePageAdapter adapter, Context context) {
        firebaseFirestore.collection("Category")
                .document("home")
                .collection("Banner")
                .orderBy("index")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // duyệt qua Firebase Storage => lấy về => lưu vào mảng
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                if ((long) snapshot.get("view_type") == 0) {
                                    List<Slider> sliderList = new ArrayList<>();
                                    long no_of_banners = (long) snapshot.get("no_of_banners");
                                    for (long x = 0; x < no_of_banners; ++x) {
                                        sliderList.add(new Slider(snapshot.get("banner_"+ x).toString(), snapshot.get("banner_" + x + "_padding").toString()));
                                    }

                                    homePageList.add(new HomePage(0, sliderList));
                                } else if ((long) snapshot.get("view_type") == 1) {
                                    homePageList.add(new HomePage(1, snapshot.get("strip_ad_banner").toString(), snapshot.get("background").toString()));
                                } else if ((long) snapshot.get("view_type") == 2) {
                                    List<HorizontalProductScroll> horizontalProductScrollList = new ArrayList<>();
                                    long no_of_products = (long) snapshot.get("no_of_products");
                                    for (long x = 0; x < no_of_products; ++x) {
//                                        int image, String title, String stuff, String price
                                        horizontalProductScrollList.add(new HorizontalProductScroll(
                                                snapshot.get("product_id_" + x).toString(),
                                                snapshot.get("product_image_" + x).toString(),
                                                snapshot.get("product_title_" + x).toString(),
                                                snapshot.get("product_size_" + x).toString(),
                                                snapshot.get("product_price_" + x).toString()
                                        ));
                                    }

                                    homePageList.add(new HomePage(2, snapshot.get("layout_title").toString(), snapshot.get("layout_background").toString(),horizontalProductScrollList));
                                } else if ((long) snapshot.get("view_type") == 3) {
                                    List<HorizontalProductScroll> gridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long) snapshot.get("no_of_products");
                                    for (long x = 0; x < no_of_products; ++x) {
//                                        int image, String title, String stuff, String price
                                        gridLayoutModelList.add(new HorizontalProductScroll(
                                                snapshot.get("product_id_" + x).toString(),
                                                snapshot.get("product_image_" + x).toString(),
                                                snapshot.get("product_title_" + x).toString(),
                                                snapshot.get("product_size_" + x).toString(),
                                                snapshot.get("product_price_" + x).toString()
                                        ));
                                    }

                                    homePageList.add(new HomePage(3, snapshot.get("layout_title").toString(), snapshot.get("layout_background").toString(),gridLayoutModelList));
                                }
                            }

                            // adapter báo cho RecyclerView => cập nhật giao diện
                            adapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
