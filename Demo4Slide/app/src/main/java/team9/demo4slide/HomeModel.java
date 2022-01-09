package team9.demo4slide;

public class HomeModel {
    public static final int IMAGE_CONTAINER = 0;
    public static final int TEXT_CONTAINER = 1;

    int type;
    String value;

    public HomeModel(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }
}
