package team9.clover.Model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

    List<String> favorite;
    String fullName;

    public UserModel() { }

    public UserModel(String fullName) {
        this.fullName = fullName;
        this.favorite = new ArrayList<>();
    }

    public List<String> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<String> favorite) {
        this.favorite = favorite;
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
}
