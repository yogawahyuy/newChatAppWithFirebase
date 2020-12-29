package com.rsip.mobile.CustomView;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.rsip.mobile.R;

import pl.droidsonroids.gif.GifImageView;

public class CustomProgressDialog {
    Activity activity;
    Dialog dialog;

    public CustomProgressDialog(Activity activity){
        this.activity=activity;
    }

    public void showDialog(){
        dialog=new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_progress_dialog);
        GifImageView gifImageView=dialog.findViewById(R.id.gifloading);
        Glide.with(activity).load(R.drawable.loading).centerCrop().into(gifImageView);
        dialog.show();
    }
    public void hideDialog(){
        dialog.dismiss();
    }
}
