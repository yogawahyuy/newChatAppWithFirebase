package com.rsip.mobile.RecylcerView;

import java.util.ArrayList;

public class RiwayatDaftarModel {

    private String title;

    private String message;


    public RiwayatDaftarModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public RiwayatDaftarModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
