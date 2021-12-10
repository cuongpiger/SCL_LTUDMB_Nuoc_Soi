package team9.clover.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team9.clover.Model.Category;
import team9.clover.Model.Slider;
import team9.clover.Module.CategoryAdapter;
import team9.clover.Module.Config;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        referWidgets(root);
        setViewCategory();
        setViewBannerSlider(root);

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
        sliderList.add(new Slider(R.drawable.ic_baseline_email_24));
        sliderList.add(new Slider(R.drawable.ic_baseline_attach_email_24));

        sliderList.add(new Slider(R.drawable.banner1));
        sliderList.add(new Slider(R.drawable.banner2));
        sliderList.add(new Slider(R.drawable.banner3));
        sliderList.add(new Slider(R.drawable.banner4));
        sliderList.add(new Slider(R.drawable.banner5));
        sliderList.add(new Slider(R.drawable.ic_baseline_person_24));
        sliderList.add(new Slider(R.drawable.ic_baseline_email_24));
        sliderList.add(new Slider(R.drawable.ic_baseline_attach_email_24));


        sliderList.add(new Slider(R.drawable.ic_bell_24));
        sliderList.add(new Slider(R.drawable.ic_cart_24));


        SliderAdapter sliderAdapter = new SliderAdapter(sliderList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);
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
}