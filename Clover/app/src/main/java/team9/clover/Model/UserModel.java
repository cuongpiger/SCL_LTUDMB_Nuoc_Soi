package team9.clover.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel {

    List<String> favorite;
    List<String> orders;
    Map<String, Integer> cart;
    String fullName;

    public UserModel() { }

    public UserModel(String fullName) {
        // dùng để tạo new user trong database
        this.fullName = fullName;
        this.favorite = new ArrayList<>();
        this.cart = new HashMap<>();
        this.orders = new ArrayList<>();
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<String> favorite) {
        this.favorite = favorite;
    }

    public Map<String, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<String, Integer> cart) {
        this.cart = cart;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void addFavorite(String productId) {
        favorite.add(productId);
    }

    public void removeFavorite(String productId) {
        favorite.remove(productId);
    }

    public void addCart(String key, int value) {
        if (cart.containsKey(key)) {
            int oldQty = (int) cart.get(key);
            cart.put(key, oldQty + value);
        } else {
            cart.put(key, value);
        }
    }
}
