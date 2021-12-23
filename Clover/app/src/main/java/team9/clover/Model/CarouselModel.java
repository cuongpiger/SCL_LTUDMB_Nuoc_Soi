package team9.clover.Model;

public class CarouselModel {

    public static final String FIRESTORAGE = "Carousel";

    String image;

    public CarouselModel() {}

    public CarouselModel(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
