package team9.clover.Model;

public class CartItemModell {

    public static final int CART_ITEM = 0;
    public static final int TOTAL_MOUNT = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /********************************************************************************** Cart item */
    String productTitle, productPrice, cuttedPrice;
    int productImage, freeCoupens, productQuantity, offersApplied, coupensApplied;

    public CartItemModell(int type, int productImage, String productTitle, int freeCoupens, String productPrice, String cuttedPrice, int productQuantity, int offersApplied, int coupensApplied) {
        this.type = type;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productImage = productImage;
        this.freeCoupens = freeCoupens;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.coupensApplied = coupensApplied;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(int offersApplied) {
        this.offersApplied = offersApplied;
    }

    public int getCoupensApplied() {
        return coupensApplied;
    }

    public void setCoupensApplied(int coupensApplied) {
        this.coupensApplied = coupensApplied;
    }
    /**********************************************************************************************/

    /********************************************************************************* Cart Total */
    private String totalItems, totalItemPrice, deliveryPrice, savedAmount, totalAmount;

    public CartItemModell(int type, String totalItems, String totalItemPrice, String deliveryPrice, String totalAmount, String savedAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPrice = deliveryPrice;
        this.totalAmount = totalAmount;
        this.savedAmount = savedAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }

    /**********************************************************************************************/
}
