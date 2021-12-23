package team9.clover.Model;

import java.util.List;

public class HomePageModel {

    public static final int CAROUSEL_VIEW_TYPE = 0;

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
}
