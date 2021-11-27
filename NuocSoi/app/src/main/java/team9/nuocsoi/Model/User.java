package team9.nuocsoi.Model;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class User {
    String fullname;
    String phone;
    String country;
    String email;
    String password;

    public User() {}

    public User(String fullname, String phone, String country, String email, String password) {
        this.fullname = fullname;
        this.phone = phone;
        this.country = country;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean matchPassword(String candidatePassword) {
        return BCrypt.checkpw(candidatePassword, this.password);
    }
}
