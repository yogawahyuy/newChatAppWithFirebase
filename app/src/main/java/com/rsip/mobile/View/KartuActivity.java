package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rsip.mobile.MainActivity;
import com.rsip.mobile.R;

public class KartuActivity extends AppCompatActivity {
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_kartu);
        backImg=findViewById(R.id.backimg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KartuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
