package com.example.chatwithfirebase.Model;

public class HomeCarousellModel {
    private int imgResourceId;
    private String headline;
    private String desc;

    public HomeCarousellModel(String headline, String desc) {
        this.headline = headline;
        this.desc = desc;
    }

    public HomeCarousellModel(int imgResourceId, String headline) {
        this.imgResourceId = imgResourceId;
        this.headline = headline;
    }

    public HomeCarousellModel(int imgResourceId) {
        this.imgResourceId = imgResourceId;
    }

    public String getDesc() {
        return desc;
    }

    public String getHeadline() {
        return headline;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }
}
