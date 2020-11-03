package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.JadwalOperasiActivity;

public class LandingJadwalOperasiActivity extends AppCompatActivity {

    EditText noKartuBpjs;
    Button btnKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_jadwal_operasi);
        findViews();
    }
    private void findViews(){
        noKartuBpjs=findViewById(R.id.edttext_noKartuBpjs);
        btnKirim=findViewById(R.id.btnKirimKartuBpjs);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (TextUtils.isEmpty(noKartuBpjs.getText().toString())){
                   Toast.makeText(LandingJadwalOperasiActivity.this, "Masukan No Kartu BPJS", Toast.LENGTH_SHORT).show();
               }else{
                   dataIntent();
               }
            }
        });
    }
    private void dataIntent(){
        Intent intent=new Intent(LandingJadwalOperasiActivity.this, JadwalOperasiActivity.class);
        intent.putExtra("nopeserta",noKartuBpjs.getText().toString());
        startActivity(intent);
    }
}