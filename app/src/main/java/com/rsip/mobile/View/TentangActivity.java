package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rsip.mobile.R;

public class TentangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}