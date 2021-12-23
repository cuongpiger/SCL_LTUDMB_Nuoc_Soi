package team9.clover.Model;

import java.util.List;

public class HomePageModel {

    public static final int CAROUSEL_VIEW_TYPE = 0,
            BANNER_VIEW_TYPE = 1;

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
}
