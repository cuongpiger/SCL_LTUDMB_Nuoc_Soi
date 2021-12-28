package team9.clover.Model;

import java.util.List;

public class CartModel {
    String id, size;
    long quantity;

    public CartModel() { }

    public CartModel(String id, String size, long quantity) {
        this.id = id;
        this.size = size;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void updateQuantity(long quantity) {
        this.quantity += quantity;
    }

    public boolean isEqual(CartModel other) {
        if (this.id == other.id && this.size == other.size) return true;
        return false;
    }

    public void addCart(List<CartModel> carts) {
        for (int i = 0; i < carts.size(); ++i) {
            if (this.isEqual(carts.get(i))) {
                carts.get(i).updateQuantity(this.quantity);
                return;
            }
        }

        carts.add(this);
    }

    public String genKey() {
        return id + "_" + size;
    }
}
