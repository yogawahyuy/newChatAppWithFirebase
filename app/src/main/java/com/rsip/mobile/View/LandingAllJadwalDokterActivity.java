package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.JadwalDokterAllActivity;
import com.rsip.mobile.RecylcerView.RiwayatDaftarActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Calendar;

public class LandingAllJadwalDokterActivity extends AppCompatActivity {

    EditText tanggalJadwal;
    Button btnCari,btnAllDokter,btnPoli;
    AlertDialog.Builder dialog;
    View dialogView;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_all_jadwal_dokter);
        findViews();
    }

    private void findViews(){
        tanggalJadwal=findViewById(R.id.edittxt_tanggaljadwal);
        tanggalJadwal.setInputType(InputType.TYPE_NULL);
        btnCari=findViewById(R.id.btn_cari);
        btnAllDokter=findViewById(R.id.btn_jadwal_semua_dokter);
        btnPoli=findViewById(R.id.btn_poli);
        tanggalJadwal.setOnClickListener(new View.OnClickListener() {
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
        btnAllDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents=new Intent(LandingAllJadwalDokterActivity.this,JadwalDokterAllActivity.class);
                intents.putExtra("doktertoday","doktertoday");
                startActivity(intents);
            }
        });
        btnPoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPoli();
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
                tanggalJadwal.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }
    private void sendData(){
        if (TextUtils.isEmpty(tanggalJadwal.getText().toString())){
            Toast.makeText(this, "Kolom Harus Terisi Semua", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent=new Intent(LandingAllJadwalDokterActivity.this, InfoSemuaDokterActivity.class);
            intent.putExtra("TANGGAL_PERIKSA",tanggalJadwal.getText().toString());
            startActivity(intent);
        }
    }
    private void showDialogPoli(){
        SearchableSpinner spinerPoli;
        dialog=new AlertDialog.Builder(this);
        inflater=getLayoutInflater();
        dialogView=inflater.inflate(R.layout.custom_layout_cari_poli,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Pilih Poliklinik");
        spinerPoli=dialogView.findViewById(R.id.spiner_poli);


        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intents=new Intent(LandingAllJadwalDokterActivity.this,JadwalDokterAllActivity.class);
                intents.putExtra("spinerPoli",spinerPoli.getSelectedItem().toString());
                intents.putExtra("poli","poli");
                startActivity(intents);
            }
        });
        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}