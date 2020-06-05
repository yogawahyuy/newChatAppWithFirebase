package com.rsip.mobile.Model;

public class GridViewModel {
    String title;
    int image;

    public GridViewModel(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }
}
