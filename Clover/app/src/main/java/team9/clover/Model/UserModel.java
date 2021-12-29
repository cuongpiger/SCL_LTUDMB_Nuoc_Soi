package team9.clover.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel {

    List<String> favorite;
    List<String> orders;
    List<String> address;
    String fullName;

    public UserModel() { }

    public UserModel(String fullName) {
        // dùng để tạo new user trong database
        this.fullName = fullName;
        this.favorite = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.address = new ArrayList<>();
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<String> favorite) {
        this.favorite = favorite;
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

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public void addFavorite(String productId) {
        favorite.add(productId);
    }

    public void removeFavorite(String productId) {
        favorite.remove(productId);
    }
}
