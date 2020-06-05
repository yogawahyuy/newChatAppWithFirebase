package com.rsip.mobile.Model;

public class AboutModel {
    String headline, deskripsi;

    public AboutModel(String headline, String deskripsi) {
        this.headline = headline;
        this.deskripsi = deskripsi;
    }

    public String getHeadline() {
        return headline;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
