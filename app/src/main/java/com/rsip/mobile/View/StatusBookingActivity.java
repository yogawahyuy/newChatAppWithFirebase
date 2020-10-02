package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rsip.mobile.MainActivity;
import com.rsip.mobile.R;

public class StatusBookingActivity extends AppCompatActivity {

    Button btn_HalamanUtama;
    TextView noAntrian,tanggal;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_booking);
        findView();
    }

    private void findView(){
        noAntrian=findViewById(R.id.noAntrian);
        tanggal=findViewById(R.id.kata_tanggal);
        btn_HalamanUtama=findViewById(R.id.btn_halamanUtama);
        btn_HalamanUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatusBookingActivity.this, MainActivity.class));
            }
        });
        intent=getIntent();
        fillFromIntent();
    }

    private void fillFromIntent(){
        noAntrian.setText( intent.getStringExtra("noAntrian"));
        String tgl="Silahkan Datang Ke Rumah Sakit Islam Purwokerto Pada Tanggal "+intent.getStringExtra("tgl")+" Untuk Konfirmasi Pendaftaran";
        tanggal.setText(tgl);
    }


}