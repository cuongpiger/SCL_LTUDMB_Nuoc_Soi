package team9.clover.Module;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team9.clover.Model.HomePage;
import team9.clover.Model.HorizontalProductScroll;
import team9.clover.Model.Slider;
import team9.clover.R;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePage> homePageList;

    public HomePageAdapter(List<HomePage> homePageList) {
        this.homePageList = homePageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePage.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout, parent, false);
                return new BannerSliderViewHolder(bannerSliderView);

            case HomePage.STRIP_AD_BANNER:
                View stripAdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdBannerViewHolder(stripAdView);

            case HomePage.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalProductViewHolder(horizontalProductView);

            case HomePage.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewHolder(gridProductView);

            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageList.get(position).getType()) {
            case 0:
                return HomePage.BANNER_SLIDER;

            case 1:
                return HomePage.STRIP_AD_BANNER;

            case 2:
                return HomePage.HORIZONTAL_PRODUCT_VIEW;

            case 3:
                return HomePage.GRID_PRODUCT_VIEW;

            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageList.get(position).getType()) {
            case HomePage.BANNER_SLIDER:
                List<Slider> sliderList = homePageList.get(position).getSliderList();
                ((BannerSliderViewHolder) holder).setBannerSliderViewPager(sliderList);
                break;

            case HomePage.STRIP_AD_BANNER:
                int resource = homePageList.get(position).getResource();
                String color = homePageList.get(position).getBackgroundColor();
                ((StripAdBannerViewHolder) holder).setStripAd(resource, color);
                break;

            case HomePage.HORIZONTAL_PRODUCT_VIEW:
                String horizontalTitle = homePageList.get(position).getTitle();
                int horizontalIcon = R.drawable.ic_baseline_discount_24;
                List<HorizontalProductScroll> horizontalProductScrollList = homePageList.get(position).getHorizontalProductScrollList();
                ((HorizontalProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollList, horizontalTitle, horizontalIcon);
                break;

            case HomePage.GRID_PRODUCT_VIEW:
                String gridTitle = homePageList.get(position).getTitle();
                int gridIcon = R.drawable.ic_new_product_24;
                List<HorizontalProductScroll> gridProductScrollList = homePageList.get(position).getHorizontalProductScrollList();
                ((GridProductViewHolder) holder).setGridProductLayout(gridProductScrollList, gridTitle, gridIcon);
                break;

            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        ViewPager bannerSliderViewPager;
        Timer timer;
        int currentPage = 2;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.vpBannerSlider);

        }

        private void setBannerSliderViewPager(final List<Slider> sliderList) {
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
                        pageLooper(sliderList);
                    }
                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
            startBannerSlideShow(sliderList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper(sliderList);
                    stopBannerSliderShow();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) startBannerSlideShow(sliderList);
                    return false;
                }
            });
        }

        private void pageLooper(List<Slider> sliderList) {
            if (currentPage == sliderList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }

            if (currentPage == 1) {
                currentPage = sliderList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
        }

        private void startBannerSlideShow(List<Slider> sliderList) {
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

    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder {

        ImageView stripAdImage;
        ConstraintLayout stripAdContainer;

        public StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.ivStripAd);
            stripAdContainer = itemView.findViewById(R.id.stripAdContainer);
        }

        private void setStripAd(int resource, String color) {
            // resource = R.drawable.banner_ad1
            // color = Color.parseColor("#D5D7D6")
            stripAdImage.setImageResource(resource);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView horizontalLayoutTitle;
        MaterialButton horizontalViewAllButton;
        RecyclerView horizontalRecyclerView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalLayoutTitle = itemView.findViewById(R.id.mtvTitle);
            horizontalViewAllButton = itemView.findViewById(R.id.mbtGridView);
            horizontalRecyclerView = itemView.findViewById(R.id.rvLayout);
        }

        private void setHorizontalProductLayout(List<HorizontalProductScroll> horizontalProductScrollList, String title, int icon) {
//            int i = R.drawable.ic_baseline_discount_24;
            horizontalLayoutTitle.setText(title);
            horizontalLayoutTitle.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            horizontalLayoutTitle.setCompoundDrawablePadding(Config.PADDING_ICON_DRAWABLE);
            horizontalLayoutTitle.getCompoundDrawables()[0].setTint(itemView.getResources().getColor(R.color.black));

            if (horizontalProductScrollList.size() > Config.NUMBER_PRODUCT_HORIZONTAL_VIEW) {
                horizontalViewAllButton.setVisibility(View.VISIBLE);
            } else {
                horizontalViewAllButton.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);
            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView titleLayout;
        MaterialButton buttonLayout;
        GridView gridViewLayout;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);

            titleLayout = itemView.findViewById(R.id.mtvGridTitle);
            buttonLayout = itemView.findViewById(R.id.mbtGridViewButton);
            gridViewLayout = itemView.findViewById(R.id.gvProductLayout);
        }

        public void setGridProductLayout(List<HorizontalProductScroll> horizontalProductScrollList, String title, int icon) {
            // R.drawable.ic_new_product_24
            titleLayout.setText(title);
            titleLayout.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            titleLayout.setCompoundDrawablePadding(Config.PADDING_ICON_DRAWABLE);
            titleLayout.getCompoundDrawables()[0].setTint(itemView.getResources().getColor(R.color.black));
            gridViewLayout.setAdapter(new GridProductAdapter(horizontalProductScrollList));
        }
    }
}
