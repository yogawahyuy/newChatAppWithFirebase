package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.RiwayatDaftarActivity;

import java.util.Calendar;

public class RiwayatPeriksaActivity extends AppCompatActivity {

    EditText editTextNoRM,editTextTglPeriksa;
    Button btnCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_periksa);
        findView();
    }

    private void findView(){
        editTextNoRM=findViewById(R.id.edittxt_NoRm);
        editTextTglPeriksa=findViewById(R.id.edittxt_tanggalperiksa);
        editTextTglPeriksa.setInputType(InputType.TYPE_NULL);
        btnCari=findViewById(R.id.btn_cari);
        editTextTglPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void showDatePicker(){
        final Calendar cldr=Calendar.getInstance();
        int day=cldr.get(Calendar.DAY_OF_MONTH);
        int month=cldr.get(Calendar.MONTH);
        int year=cldr.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextTglPeriksa.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void sendData(){
        if (TextUtils.isEmpty(editTextNoRM.getText().toString())){
            Toast.makeText(this, "Kolom Harus Terisi Semua", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent=new Intent(RiwayatPeriksaActivity.this, RiwayatDaftarActivity.class);
            intent.putExtra("KD_REKAM_MEDIS",editTextNoRM.getText().toString());
            intent.putExtra("TANGGAL_PERIKSA",editTextTglPeriksa.getText().toString());
            startActivity(intent);
        }
    }
}
