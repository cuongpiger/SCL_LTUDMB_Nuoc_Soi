package team9.clover.Model;

import java.util.List;

public class HomePageModel {

    public static final int CAROUSEL_VIEW_TYPE = 0,
            BANNER_VIEW_TYPE = 1,
            SLIDER_PRODUCT_VIEW_TYPE = 3;

    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /* Thiết lập cho phần Carousel của Home Fragment **********************************************/
    List<CarouselModel> carouselModelList;

    public HomePageModel(List<CarouselModel> carouselModelList) {
        this.carouselModelList = carouselModelList;
        this.type = CAROUSEL_VIEW_TYPE;
    }

    public List<CarouselModel> getCarouselModelList() {
        return carouselModelList;
    }

    public void setCarouselModelList(List<CarouselModel> carouselModelList) {
        this.carouselModelList = carouselModelList;
    }
    //______________________________________________________________________________________________

    /* Thiết lập cho phần Banner của Home Fragment ************************************************/
    BannerModel banner;

    public HomePageModel(BannerModel banner) {
        this.banner = banner;
        this.type = BANNER_VIEW_TYPE;
    }

    public BannerModel getBanner() {
        return banner;
    }

    public void setBanner(BannerModel banner) {
        this.banner = banner;
    }
    //______________________________________________________________________________________________

    /* Thiết lập cho phần product slider ở Fragment home ******************************************/
    List<ProductModel> sliderProductModelList;
    int icon;
    String title;

    public HomePageModel(int type, int icon, String title, List<ProductModel> sliderProductModelList) {
        this.type = type;
        this.icon = icon;
        this.title = title;
        this.sliderProductModelList = sliderProductModelList;
    }

    public List<ProductModel> getSliderProductModelList() {
        return sliderProductModelList;
    }

    public void setSliderProductModelList(List<ProductModel> sliderProductModelList) {
        this.sliderProductModelList = sliderProductModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
    //______________________________________________________________________________________________

}
