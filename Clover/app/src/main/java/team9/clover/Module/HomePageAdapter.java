package team9.clover.Module;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team9.clover.Model.HomePage;
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
}
