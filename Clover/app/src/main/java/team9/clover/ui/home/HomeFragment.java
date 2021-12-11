package team9.clover.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team9.clover.Model.Category;
import team9.clover.Model.HorizontalProductScroll;
import team9.clover.Model.Slider;
import team9.clover.Module.CategoryAdapter;
import team9.clover.Module.Config;
import team9.clover.Module.GridProductAdapter;
import team9.clover.Module.HorizontalProductScrollAdapter;
import team9.clover.Module.SliderAdapter;
import team9.clover.R;
import team9.clover.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    RecyclerView categoryRecyclerView;
    CategoryAdapter categoryAdapter;

    ViewPager bannerSliderViewPager;
    List<Slider> sliderList;
    Timer timer;
    int currentPage = 2;

    ImageView stripAdImage;
    ConstraintLayout stripAdContainer;

    // Horizontal product layout
    MaterialTextView horizontalLayoutTitle;
    MaterialButton horizontalViewAllButton;
    RecyclerView horizontalRecyclerView;
    //________________________________

    // Grid product layout

    //________________________________

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        referWidgets(root);
        setViewCategory();
        setViewBannerSlider(root);
        setViewAdImage(root);
        setHorizontalProduct(root);
        setGridLayoutProduct(root);

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

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("link", "Trang chủ"));
        categoryList.add(new Category("link", "Đồ bộ"));
        categoryList.add(new Category("link", "Áo"));
        categoryList.add(new Category("link", "Quần"));
        categoryList.add(new Category("link", "Đầm"));
        categoryList.add(new Category("link", "Chân váy"));
        categoryList.add(new Category("link", "Giày"));
        categoryList.add(new Category("link", "Phụ kiện"));
        categoryAdapter = new CategoryAdapter(categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    private void setViewBannerSlider(View view) {
        bannerSliderViewPager = view.findViewById(R.id.vpBannerSlider);
        sliderList = new ArrayList<Slider>();
        sliderList.add(new Slider(R.drawable.banner6, "#EFEFEF"));
        sliderList.add(new Slider(R.drawable.banner7, "#7696A5"));

        sliderList.add(new Slider(R.drawable.banner1, "#EF6540"));
        sliderList.add(new Slider(R.drawable.banner2, "#988F7E"));
        sliderList.add(new Slider(R.drawable.banner3, "#898989"));
        sliderList.add(new Slider(R.drawable.banner4, "#775440"));
        sliderList.add(new Slider(R.drawable.banner5, "#FAC6CD"));
        sliderList.add(new Slider(R.drawable.banner6, "#EFEFEF"));
        sliderList.add(new Slider(R.drawable.banner7, "#7696A5"));


        sliderList.add(new Slider(R.drawable.banner1, "#EF6540"));
        sliderList.add(new Slider(R.drawable.banner2, "#988F7E"));


        SliderAdapter sliderAdapter = new SliderAdapter(sliderList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);
        bannerSliderViewPager.setCurrentItem(currentPage);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pageLooper();
                }
            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
        startBannerSlideShow();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pageLooper();
                stopBannerSliderShow();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) startBannerSlideShow();
                return false;
            }
        });
    }

    private void pageLooper() {
        if (currentPage == sliderList.size() - 2) {
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }

        if (currentPage == 1) {
            currentPage = sliderList.size() - 3;
            bannerSliderViewPager.setCurrentItem(currentPage, false);
        }
    }

    private void startBannerSlideShow() {
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderList.size()) currentPage = 1;
                bannerSliderViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, Config.BANNER_SLIDER_DELAY, Config.BANNER_SLIDER_PERIOD);
    }

    private void stopBannerSliderShow() {
        timer.cancel();
    }

    private void setViewAdImage(View view) {
        stripAdImage = view.findViewById(R.id.ivStripAd);
        stripAdContainer = view.findViewById(R.id.stripAdContainer);

        stripAdImage.setImageResource(R.drawable.banner_ad1);
        stripAdContainer.setBackgroundColor(Color.parseColor("#D5D7D6"));
    }

    private void setHorizontalProduct(View view) {
        horizontalLayoutTitle = view.findViewById(R.id.mtvTitle);
        horizontalViewAllButton = view.findViewById(R.id.mbtGridView);
        horizontalRecyclerView = view.findViewById(R.id.rvLayout);

        horizontalLayoutTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_discount_24, 0, 0, 0);
        horizontalLayoutTitle.setCompoundDrawablePadding(Config.PADDING_ICON_DRAWABLE);
        horizontalLayoutTitle.getCompoundDrawables()[0].setTint(getResources().getColor(R.color.black));

        List<HorizontalProductScroll> horizontalProductScrollList = new ArrayList<>();
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));
        horizontalProductScrollList.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

    }

    private void setGridLayoutProduct(View view) {
        MaterialTextView titleLayout = view.findViewById(R.id.mtvGridTitle);
        MaterialButton buttonLayout = view.findViewById(R.id.mbtGridViewButton);
        GridView gridViewLayout = view.findViewById(R.id.gvProductLayout);

        titleLayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_basket_24, 0, 0, 0);
        titleLayout.setCompoundDrawablePadding(Config.PADDING_ICON_DRAWABLE);
        titleLayout.getCompoundDrawables()[0].setTint(getResources().getColor(R.color.black));

        List<HorizontalProductScroll> horizontalProductGridView = new ArrayList<>();
        horizontalProductGridView.add(new HorizontalProductScroll(R.drawable.product1, "Olympiah", "Vải đũi", "720.000 đ"));
        horizontalProductGridView.add(new HorizontalProductScroll(R.drawable.product1, "Olympiah", "Vải đũi", "720.000 đ"));
        horizontalProductGridView.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));
        horizontalProductGridView.add(new HorizontalProductScroll(R.drawable.hz_product1, "Paradiso blazer", "Vải flannel", "1.390.000 đ"));


        GridProductAdapter gridProductAdapter = new GridProductAdapter(horizontalProductGridView);
        gridViewLayout.setAdapter(gridProductAdapter);
        gridProductAdapter.notifyDataSetChanged();

    }
}