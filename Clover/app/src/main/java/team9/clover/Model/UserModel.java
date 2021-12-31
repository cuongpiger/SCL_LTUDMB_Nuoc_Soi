package team9.clover.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel {

    List<String> favorite;
    List<String> address;
    String fullName, phone, order;

    public UserModel() { }

    public UserModel(String fullName) {
        // dùng để tạo new user trong database
        this.fullName = fullName;
        this.favorite = new ArrayList<>();
        this.address = new ArrayList<>();
        this.order = "";
        this.phone = "";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<String> favorite) {
        this.favorite = favorite;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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
