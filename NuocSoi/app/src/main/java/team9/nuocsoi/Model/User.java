package team9.nuocsoi.Model;

import java.io.Serializable;

public class User implements Serializable {
    String fullname;
    String phone;
    String country;
    String email;
    String role;

    public User() {}

    public User(String fullname, String country, String phone, String email, String role) {
        this.fullname = fullname;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String takePhoneNumberWithPlus() {
        return "+" + this.country + this.phone;
    }
}
