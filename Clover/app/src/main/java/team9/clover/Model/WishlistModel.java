package team9.clover.Model;

public class WishlistModel {

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public int getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(int freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    int productImage, freeCoupens, totalRatings;
    String productTitle, rating, productPrice, cuttedPrice;

    public WishlistModel(int productImage, String productTitle, int freeCoupens, String rating, int totalRatings, String productPrice, String cuttedPrice) {
        this.productImage = productImage;
        this.freeCoupens = freeCoupens;
        this.totalRatings = totalRatings;
        this.productTitle = productTitle;
        this.rating = rating;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
    }




}
