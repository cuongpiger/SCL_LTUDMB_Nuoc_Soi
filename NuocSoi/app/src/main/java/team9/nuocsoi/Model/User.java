package team9.nuocsoi.Model;

public class User {
    int type;
    String fullname;
    String phone;
    String email;
    String password;

    public User() {}

    public User(int type, String fullname, String phone, String email, String password) {
        this.type = type;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
