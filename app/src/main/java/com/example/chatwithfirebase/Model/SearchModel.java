package com.example.chatwithfirebase.Model;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchModel implements Searchable {

    private String mTitle;

    public SearchModel(String mTitle) {
       this.mTitle = mTitle;
    }

    public SearchModel setmTitle(String mTitle) {
        mTitle = mTitle;
        return this;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }
}
