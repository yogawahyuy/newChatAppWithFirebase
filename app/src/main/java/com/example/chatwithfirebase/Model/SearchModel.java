package com.example.chatwithfirebase.Model;



public class SearchModel{

    private String mTitle;

    public SearchModel(String mTitle) {
       this.mTitle = mTitle;
    }

    public SearchModel setmTitle(String mTitle) {
        mTitle = mTitle;
        return this;
    }

//    @Override
//    public String getTitle() {
//        return mTitle;
//    }
}
