package team9.clover.Model;

import java.util.List;

public class HomePage {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER = 1;

    private int type;

    // Banner slider
    private List<Slider> sliderList;
    public HomePage(int type, List<Slider> sliderList) {
        this.type = type;
        this.sliderList = sliderList;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public List<Slider> getSliderList() {
        return sliderList;
    }
    public void setSliderList(List<Slider> sliderList) {
        this.sliderList = sliderList;
    }
    //____________________________________________________

    // Strip ad
    private int resource;
    private String backgroundColor;
    public HomePage(int type, int resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }
    public int getResource() {
        return resource;
    }
    public void setResource(int resource) {
        this.resource = resource;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    //____________________________________________________


}
