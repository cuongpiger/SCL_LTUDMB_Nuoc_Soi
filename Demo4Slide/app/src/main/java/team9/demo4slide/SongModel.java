package team9.demo4slide;

import java.util.ArrayList;

public class SongModel {
    String title;
    ArrayList<String> split;

    // bắt buộc
    public SongModel() {}

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public ArrayList<String> getSplit() { return split; }

    public void setSplit(ArrayList<String> split) { this.split = split; }
}
