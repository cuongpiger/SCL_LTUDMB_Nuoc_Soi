package team9.clover.Model;

public class HorizontalProductScroll {

    private String productId;
    private String image;
    private String title;
    private String stuff;
    private String price;

    public HorizontalProductScroll(String productId, String image, String title, String stuff, String price) {
        this.productId = productId;
        this.image = image;
        this.title = title;
        this.stuff = stuff;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStuff() {
        return stuff;
    }

    public void setStuff(String stuff) {
        this.stuff = stuff;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
