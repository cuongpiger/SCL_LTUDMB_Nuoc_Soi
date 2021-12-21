package team9.clover.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team9.clover.Model.Category;
import team9.clover.Model.HomePage;
import team9.clover.Model.HorizontalProductScroll;
import team9.clover.Model.Slider;
import team9.clover.Module.CategoryAdapter;
import team9.clover.Module.Config;
import team9.clover.Module.GridProductAdapter;
import team9.clover.Module.HomePageAdapter;
import team9.clover.Module.HorizontalProductScrollAdapter;
import team9.clover.Module.SliderAdapter;
import team9.clover.R;
import team9.clover.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView categoryRecyclerView;
    RecyclerView rvHomePage;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    HomePageAdapter adapter;
    FirebaseFirestore firebaseFirestore;

    public HomeFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        referWidgets(root);
        setViewCategory();
        setViewRemaining(root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void referWidgets(View view) {
        categoryRecyclerView = view.findViewById(R.id.rvCategory);
    }

    private void setViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
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
                            categoryAdapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setViewRemaining(View view) {
        rvHomePage = view.findViewById(R.id.rvHomePage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHomePage.setLayoutManager(linearLayoutManager);
        List<HomePage> homePageList = new ArrayList<>();
        adapter = new HomePageAdapter(homePageList);
        rvHomePage.setAdapter(adapter);

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

                                } else if ((long) snapshot.get("view_type") == 3) {

                                }
                            }

                            // adapter báo cho RecyclerView => cập nhật giao diện
                            adapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



//        List<Slider> sliderList = new ArrayList<Slider>();
//        sliderList.add(new Slider(R.drawable.banner6, "#EFEFEF"));
//        sliderList.add(new Slider(R.drawable.banner7, "#7696A5"));
//        sliderList.add(new Slider(R.drawable.banner1, "#EF6540"));
//        sliderList.add(new Slider(R.drawable.banner2, "#988F7E"));
//        sliderList.add(new Slider(R.drawable.banner3, "#898989"));
//        sliderList.add(new Slider(R.drawable.banner4, "#775440"));
//        sliderList.add(new Slider(R.drawable.banner5, "#FAC6CD"));
//        sliderList.add(new Slider(R.drawable.banner6, "#EFEFEF"));
//        sliderList.add(new Slider(R.drawable.banner7, "#7696A5"));
//        sliderList.add(new Slider(R.drawable.banner1, "#EF6540"));
//        sliderList.add(new Slider(R.drawable.banner2, "#988F7E"));

//        List<HorizontalProductScroll> horizontalProductScrollList = new ArrayList<>();
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "M  L  XL", "1.390.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "S  M  L", "1.390.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.product1, "Olympiah", "XS  S", "720.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.product1, "Olympiah", "L  XL", "720.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "S  M  L", "1.390.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "XS  S", "1.390.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "L  XL", "1.390.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "L", "1.390.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "XS", "1.390.000 đ"));
//        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "XL", "1.390.000 đ"));





        // ---------------
    }
}