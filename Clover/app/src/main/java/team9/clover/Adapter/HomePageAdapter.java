package team9.clover.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import team9.clover.Fragment.ProductDetailFragment;
import team9.clover.Fragment.SpecificProductFragment;
import team9.clover.Model.BannerModel;
import team9.clover.Model.CarouselModel;
import team9.clover.Model.HomePageModel;
import team9.clover.Model.ProductModel;
import team9.clover.R;

public class HomePageAdapter extends RecyclerView.Adapter {

    List<HomePageModel> homePageModelList;
    RecyclerView.RecycledViewPool recycledViewPool;
    RecyclerView.RecycledViewPool recycledViewPool2;


    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool2 = new RecyclerView.RecycledViewPool();
        this.homePageModelList.sort(Comparator.comparing(HomePageModel::getType));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePageModel.CAROUSEL_VIEW_TYPE:
                return new CarouselViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.container_carousel, parent, false));

            case HomePageModel.BANNER_VIEW_TYPE:
                return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.container_banner, parent, false));

            case HomePageModel.NEW_PRODUCT_VIEW_TYPE:
                return new NewProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.container_new_product, parent, false));

            case HomePageModel.SLIDER_PRODUCT_VIEW_TYPE:
                return new SliderProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.container_slider_product, parent, false));

            case HomePageModel.GRID_PRODUCT_VIEW_TYPE:
                return new GridProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.container_grid_product, parent, false));

            case HomePageModel.FOOTER_VIEW_TYPE:
                return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footer, parent, false));

            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.CAROUSEL_VIEW_TYPE:
                return HomePageModel.CAROUSEL_VIEW_TYPE;

            case HomePageModel.BANNER_VIEW_TYPE:
                return HomePageModel.BANNER_VIEW_TYPE;

            case HomePageModel.NEW_PRODUCT_VIEW_TYPE:
                return HomePageModel.NEW_PRODUCT_VIEW_TYPE;

            case HomePageModel.SLIDER_PRODUCT_VIEW_TYPE:
                return HomePageModel.SLIDER_PRODUCT_VIEW_TYPE;

            case HomePageModel.GRID_PRODUCT_VIEW_TYPE:
                return HomePageModel.GRID_PRODUCT_VIEW_TYPE;

            case HomePageModel.FOOTER_VIEW_TYPE:
                return HomePageModel.FOOTER_VIEW_TYPE;

            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.CAROUSEL_VIEW_TYPE:
                ((CarouselViewHolder) holder).set(homePageModelList.get(position).getCarouselModelList());
                break;

            case HomePageModel.BANNER_VIEW_TYPE:
                BannerModel bannerModel = homePageModelList.get(position).getBanner();
                ((BannerViewHolder) holder).set(bannerModel.getImage(), bannerModel.getPadding());
                break;

            case HomePageModel.NEW_PRODUCT_VIEW_TYPE:
                ((NewProductViewHolder) holder).set(homePageModelList.get(position).getProductModelList());
                break;

            case HomePageModel.SLIDER_PRODUCT_VIEW_TYPE:
                HomePageModel sliderProduct = homePageModelList.get(position);
                ((SliderProductViewHolder) holder).set(
                        sliderProduct.getProductModelList(),
                        sliderProduct.getIcon(),
                        sliderProduct.getTitle());
                break;

            case HomePageModel.GRID_PRODUCT_VIEW_TYPE:
                HomePageModel gridProduct = homePageModelList.get(position);
                ((GridProductViewHolder) holder).set(
                        gridProduct.getProductModelList(),
                        gridProduct.getIcon(),
                        gridProduct.getTitle());
                break;

            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {

        ViewPager mContainer;
        Timer timer;
        int currentPage;
        List<CarouselModel> carouselModelList;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.vpContainer);

        }

        private void set(final List<CarouselModel> carouselModels) {
            currentPage = 2;
            if (timer != null) {
                timer.cancel();
            }

            carouselModelList = new ArrayList<>();
            for (int x = 0; x < carouselModels.size(); ++x) {
                carouselModelList.add(x, carouselModels.get(x));
            }

            carouselModelList.add(0, carouselModels.get(carouselModels.size() - 2));
            carouselModelList.add(1, carouselModels.get(carouselModels.size() - 1));
            carouselModelList.add(carouselModels.get(0));
            carouselModelList.add(carouselModels.get(1));


            CarouselAdapter adapter = new CarouselAdapter(carouselModelList);
            mContainer.setAdapter(adapter);
            mContainer.setClipToPadding(false);
            mContainer.setPageMargin(20);
            mContainer.setCurrentItem(currentPage);
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
                        pageLooper(carouselModelList);
                    }
                }
            };
            mContainer.addOnPageChangeListener(onPageChangeListener);
            startCarousel(carouselModelList);

            mContainer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper(carouselModelList);
                    stopCarousel();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) startCarousel(carouselModelList);
                    return false;
                }
            });
        }

        private void pageLooper(List<CarouselModel> carouselModelList) {
            if (currentPage == carouselModelList.size() - 2) {
                currentPage = 2;
                mContainer.setCurrentItem(currentPage, false);
            }

            if (currentPage == 1) {
                currentPage = carouselModelList.size() - 3;
                mContainer.setCurrentItem(currentPage, false);
            }
        }

        private void startCarousel(List<CarouselModel> carouselModelList) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= carouselModelList.size()) currentPage = 1;
                    mContainer.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, 3000, 3000);
        }

        private void stopCarousel() {
            timer.cancel();
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        ConstraintLayout mContainer;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.ivImage);
            mContainer = itemView.findViewById(R.id.clContainer);
        }

        private void set(String imageUrl, String color) {
            Glide.with(itemView.getContext()).load(imageUrl).into(mImage);
            mContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class NewProductViewHolder extends RecyclerView.ViewHolder {

        RecyclerView mContainer;

        public NewProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.rvContainer);
            mContainer.setRecycledViewPool(recycledViewPool2);
        }

        private void set(List<ProductModel> productModelList) {
            NewProductAdapter adapter = new NewProductAdapter(productModelList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mContainer.setLayoutManager(layoutManager);
            mContainer.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public class SliderProductViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView mTitle;
        MaterialButton mViewAll;
        RecyclerView mContainer;

        public SliderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.mtvTitle);
            mViewAll = itemView.findViewById(R.id.mbViewAll);
            mContainer = itemView.findViewById(R.id.rvContainer);
            mContainer.setRecycledViewPool(recycledViewPool);
        }

        private void set(List<ProductModel> productModelList, int icon, String title) {
            mTitle.setText(title);
            mTitle.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);

            SliderProductAdapter adapter = new SliderProductAdapter(productModelList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mContainer.setLayoutManager(layoutManager);
            mContainer.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            mViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("broadcast");
                    intent.putExtra(SpecificProductFragment.class.getSimpleName(), -1);
                    LocalBroadcastManager.getInstance(itemView.getContext()).sendBroadcast(intent);
                }
            });
        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView mTitle;
        MaterialButton mViewAll;
        GridLayout mContainer;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.mtvProductTitle);
            mViewAll = itemView.findViewById(R.id.mbViewAll);
            mContainer = itemView.findViewById(R.id.glContainer);
        }

        public void set(List<ProductModel> productModelList, int icon, String title) {
            mTitle.setText(title);
            mTitle.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);

            for (int i = 0; i < 4; ++i) {
                View mChild = mContainer.getChildAt(i);
                ImageView mImageItem = mChild.findViewById(R.id.ivImage);
                MaterialTextView mTitleItem = mChild.findViewById(R.id.mtvProductTitle),
                        mSizeItem = mChild.findViewById(R.id.mtvSize),
                        mPriceItem = mChild.findViewById(R.id.mtvPrice);

                ProductModel productModel = productModelList.get(i);
                Glide.with(itemView.getContext()).load(productModel.getImage().get(0)).into(mImageItem);
                mTitleItem.setText(productModel.getTitle());
                mSizeItem.setText(String.join("  ", productModel.getSize()));
                mPriceItem.setText(productModel.getPrice());

                /*
                * Sự kiện khi user nhấn vào sản phẩm tương ứng sẽ đưa user đến trang chi tiết của sản phẩm đó
                * */
                mChild.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent("broadcast");
                        intent.putExtra(ProductDetailFragment.class.getSimpleName(), productModel);
                        LocalBroadcastManager.getInstance(itemView.getContext()).sendBroadcast(intent);
                    }
                });

            }

            /*
            * Sự kiện khi user nhấn vào button xem thêm
            * */
            mViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("broadcast");
                    intent.putExtra(SpecificProductFragment.class.getSimpleName(), -2);
                    LocalBroadcastManager.getInstance(itemView.getContext()).sendBroadcast(intent);
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


//public class HomePageAdapter extends RecyclerView.Adapter {
//
//    private List<HomePage> homePageList;
//    private RecyclerView.RecycledViewPool recycledViewPool;
//
//    public HomePageAdapter(List<HomePage> homePageList) {
//        this.homePageList = homePageList;
//        recycledViewPool = new RecyclerView.RecycledViewPool();
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case HomePage.BANNER_SLIDER:
//                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout, parent, false);
//                return new BannerSliderViewHolder(bannerSliderView);
//
//            case HomePage.STRIP_AD_BANNER:
//                View stripAdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
//                return new StripAdBannerViewHolder(stripAdView);
//
//            case HomePage.HORIZONTAL_PRODUCT_VIEW:
//                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
//                return new HorizontalProductViewHolder(horizontalProductView);
//
//            case HomePage.GRID_PRODUCT_VIEW:
//                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
//                return new GridProductViewHolder(gridProductView);
//
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        switch (homePageList.get(position).getType()) {
//            case 0:
//                return HomePage.BANNER_SLIDER;
//
//            case 1:
//                return HomePage.STRIP_AD_BANNER;
//
//            case 2:
//                return HomePage.HORIZONTAL_PRODUCT_VIEW;
//
//            case 3:
//                return HomePage.GRID_PRODUCT_VIEW;
//
//            default:
//                return -1;
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        switch (homePageList.get(position).getType()) {
//            case HomePage.BANNER_SLIDER:
//                List<Slider> sliderList = homePageList.get(position).getSliderList();
//                ((BannerSliderViewHolder) holder).setBannerSliderViewPager(sliderList);
//                break;
//
//            case HomePage.STRIP_AD_BANNER:
//                String resource = homePageList.get(position).getResource();
//                String color = homePageList.get(position).getBackgroundColor();
//                ((StripAdBannerViewHolder) holder).setStripAd(resource, color);
//                break;
//
//            case HomePage.HORIZONTAL_PRODUCT_VIEW:
//                String horizontalTitle = homePageList.get(position).getTitle();
//                String layoutColor = homePageList.get(position).getBackgroundColor();
//                int horizontalIcon = R.drawable.ic_baseline_discount_24;
//                List<WishlistModel> viewAllProductList = homePageList.get(position).getViewAllProductList();
//                List<HorizontalProductScroll> horizontalProductScrollList = homePageList.get(position).getHorizontalProductScrollList();
//                ((HorizontalProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollList, horizontalTitle, horizontalIcon, layoutColor, viewAllProductList);
//                break;
//
//            case HomePage.GRID_PRODUCT_VIEW:
//                String gridTitle = homePageList.get(position).getTitle();
//                String gridColor = homePageList.get(position).getBackgroundColor();
//                int gridIcon = R.drawable.ic_new_product_24;
//                List<HorizontalProductScroll> gridProductScrollList = homePageList.get(position).getHorizontalProductScrollList();
//                ((GridProductViewHolder) holder).setGridProductLayout(gridProductScrollList, gridTitle, gridIcon, gridColor);
//                break;
//
//            default:
//                return;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return homePageList.size();
//    }
//
//    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {
//
//        ViewPager bannerSliderViewPager;
//        Timer timer;
//        int currentPage;
//        private List<Slider> arrangedList;
//
//        public BannerSliderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            bannerSliderViewPager = itemView.findViewById(R.id.vpBannerSlider);
//
//        }
//
//        private void setBannerSliderViewPager(final List<Slider> sliderList) {
//            currentPage = 2;
//            if (timer != null) {
//                timer.cancel();
//            }
//
//            arrangedList = new ArrayList<>();
//            for (int x = 0; x < sliderList.size(); ++x) {
//                arrangedList.add(x, sliderList.get(x));
//            }
//
//            arrangedList.add(0, sliderList.get(sliderList.size() - 2));
//            arrangedList.add(1, sliderList.get(sliderList.size() - 1));
//            arrangedList.add(sliderList.get(0));
//            arrangedList.add(sliderList.get(1));
//
//
//            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
//            bannerSliderViewPager.setAdapter(sliderAdapter);
//            bannerSliderViewPager.setClipToPadding(false);
//            bannerSliderViewPager.setPageMargin(20);
//            bannerSliderViewPager.setCurrentItem(currentPage);
//            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    currentPage = position;
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//                    if (state == ViewPager.SCROLL_STATE_IDLE) {
//                        pageLooper(arrangedList);
//                    }
//                }
//            };
//            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
//            startBannerSlideShow(arrangedList);
//
//            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    pageLooper(arrangedList);
//                    stopBannerSliderShow();
//                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) startBannerSlideShow(arrangedList);
//                    return false;
//                }
//            });
//        }
//
//        private void pageLooper(List<Slider> sliderList) {
//            if (currentPage == sliderList.size() - 2) {
//                currentPage = 2;
//                bannerSliderViewPager.setCurrentItem(currentPage, false);
//            }
//
//            if (currentPage == 1) {
//                currentPage = sliderList.size() - 3;
//                bannerSliderViewPager.setCurrentItem(currentPage, false);
//            }
//        }
//
//        private void startBannerSlideShow(List<Slider> sliderList) {
//            Handler handler = new Handler();
//            Runnable update = new Runnable() {
//                @Override
//                public void run() {
//                    if (currentPage >= sliderList.size()) currentPage = 1;
//                    bannerSliderViewPager.setCurrentItem(currentPage++, true);
//                }
//            };
//
//            timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    handler.post(update);
//                }
//            }, Config.BANNER_SLIDER_DELAY, Config.BANNER_SLIDER_PERIOD);
//        }
//
//        private void stopBannerSliderShow() {
//            timer.cancel();
//        }
//    }
//
//    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView stripAdImage;
//        ConstraintLayout stripAdContainer;
//
//        public StripAdBannerViewHolder(@NonNull View itemView) {
//            super(itemView);
//            stripAdImage = itemView.findViewById(R.id.ivStripAd);
//            stripAdContainer = itemView.findViewById(R.id.stripAdContainer);
//        }
//
//        private void setStripAd(String resource, String color) {
//            // resource = R.drawable.banner_ad1
//            // color = Color.parseColor("#D5D7D6")
////            stripAdImage.setImageResource(resource);
//
//            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.banner_ad1)).into((stripAdImage));
//
//            stripAdContainer.setBackgroundColor(Color.parseColor(color));
//        }
//    }
//
//    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder {
//
//        ConstraintLayout container;
//        MaterialTextView horizontalLayoutTitle;
//        MaterialButton horizontalViewAllButton;
//        RecyclerView horizontalRecyclerView;
//
//        public HorizontalProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            container = itemView.findViewById(R.id.container);
//            horizontalLayoutTitle = itemView.findViewById(R.id.mtvTitle);
//            horizontalViewAllButton = itemView.findViewById(R.id.mbtGridView);
//            horizontalRecyclerView = itemView.findViewById(R.id.rvLayout);
//            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
//        }
//
//        private void setHorizontalProductLayout(List<HorizontalProductScroll> horizontalProductScrollList, String title, int icon, String color, List<WishlistModel> viewAllProductList) {
////            int i = R.drawable.ic_baseline_discount_24;
//            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
//            horizontalLayoutTitle.setText(title);
//            horizontalLayoutTitle.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
//            horizontalLayoutTitle.setCompoundDrawablePadding(Config.PADDING_ICON_DRAWABLE);
//            horizontalLayoutTitle.getCompoundDrawables()[0].setTint(itemView.getResources().getColor(R.color.black));
//
//            // set button view all for horizontal layout
//            if (horizontalProductScrollList.size() > Config.NUMBER_PRODUCT_HORIZONTAL_VIEW) {
//                horizontalViewAllButton.setVisibility(View.VISIBLE);
//                horizontalViewAllButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ViewAllActivity.wishlistModelList = viewAllProductList;
//                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
//                        viewAllIntent.putExtra("layout_code", 0);
//                        viewAllIntent.putExtra("title", title);
//                        itemView.getContext().startActivity(viewAllIntent);
//
//                    }
//                });
//            } else {
//                horizontalViewAllButton.setVisibility(View.INVISIBLE);
//            }
//
//            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollList);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            horizontalRecyclerView.setLayoutManager(linearLayoutManager);
//            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
//            horizontalProductScrollAdapter.notifyDataSetChanged();
//        }
//    }
//
//    public class GridProductViewHolder extends RecyclerView.ViewHolder {
//
//        ConstraintLayout container;
//        MaterialTextView titleLayout;
//        MaterialButton buttonLayout;
//        GridLayout gridProductLayout;
//
//        public GridProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            container = itemView.findViewById(R.id.container);
//            titleLayout = itemView.findViewById(R.id.mtvGridTitle);
//            buttonLayout = itemView.findViewById(R.id.mbt_view_all);
//            gridProductLayout = itemView.findViewById(R.id.grid_layout);
//        }
//
//        public void setGridProductLayout(List<HorizontalProductScroll> horizontalProductScrollList, String title, int icon, String color) {
//            // R.drawable.ic_new_product_24
//            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
//            titleLayout.setText(title);
//            titleLayout.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
//            titleLayout.setCompoundDrawablePadding(Config.PADDING_ICON_DRAWABLE);
//            titleLayout.getCompoundDrawables()[0].setTint(itemView.getResources().getColor(R.color.black));
//
//            for (int x = 0; x < 4; ++x) {
//                View child = gridProductLayout.getChildAt(x);
//                ImageView productImage = child.findViewById(R.id.ivProduct);
//                MaterialTextView productTitle = child.findViewById(R.id.mtvProductTitle),
//                        productSize = child.findViewById(R.id.mtvSize),
//                        productPrice = child.findViewById(R.id.mtvPrice);
//
//                HorizontalProductScroll product = horizontalProductScrollList.get(x);
//                Glide.with(itemView.getContext()).load(horizontalProductScrollList.get(x).getImage()).apply(new RequestOptions().placeholder(R.drawable.product1)).into(productImage);
//
//                productTitle.setText(product.getTitle());
//                productSize.setText(product.getStuff());
//                productPrice.setText(product.getPrice() + " đ");
//
//                child.setBackgroundColor(itemView.getContext().getColor(R.color.white));
//                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
//                        itemView.getContext().startActivity(productDetailsIntent);
//                    }
//                });
//
//            }
//
//            // button view all of grid product layout
//            buttonLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ViewAllActivity.horizontalProductScrollList = horizontalProductScrollList; // assign data for view all activity
//                    Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
//                    viewAllIntent.putExtra("layout_code", 1);
//                    viewAllIntent.putExtra("title", title);
//                    itemView.getContext().startActivity(viewAllIntent);
//                }
//            });
//        }
//    }
//}
