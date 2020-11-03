package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rsip.mobile.R;

public class DetailAmbulanceKeluarActivity extends AppCompatActivity {
    Intent intent;

    TextView nopol,driver,tujuan,jamKeluar,jarak,jenisKendaraan,tanggal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ambulance_keluar);
        findView();
    }
    private void findView(){
        intent=getIntent();
        nopol=findViewById(R.id.textPlatNomer);
        driver=findViewById(R.id.textdriver);
        tujuan=findViewById(R.id.texttujuan);
        jamKeluar=findViewById(R.id.textjamKeluar);
        jarak=findViewById(R.id.textjarak);
        jenisKendaraan=findViewById(R.id.textjenis);
        tanggal=findViewById(R.id.texttanggal);
        fillFromIntent();
    }

    private void fillFromIntent(){
        String jaraknya=intent.getStringExtra("jarak");
        nopol.setText(intent.getStringExtra("NoPlat"));
        driver.setText(intent.getStringExtra("driver"));
        tujuan.setText(intent.getStringExtra("tujuan"));
        jamKeluar.setText(intent.getStringExtra("jamInput"));
        jarak.setText(jaraknya+" KM");
        jenisKendaraan.setText(intent.getStringExtra("jenisKendaraan"));
        tanggal.setText(intent.getStringExtra("tanggalInput"));
    }
}