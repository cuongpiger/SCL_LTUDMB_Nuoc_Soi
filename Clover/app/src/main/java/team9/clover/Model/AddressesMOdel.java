package team9.clover.Model;

public class AddressesMOdel {
    String fullname, address, phone;

    public AddressesMOdel(String fullname, String address, String phone) {
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
