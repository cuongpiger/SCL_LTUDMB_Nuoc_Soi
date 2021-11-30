package team9.nuocsoi.Model;

import java.io.Serializable;

public class Customer extends User implements Serializable {
    public Customer() {
        super();
    }

    public Customer(String fullname, String country, String phone, String email) {
        super(fullname, country, phone, email, Customer.class.getSimpleName());
    }
}
