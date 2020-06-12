package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rsip.mobile.Model.JadwalSolatModel;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.JsonUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class JadwalSholatActivity extends AppCompatActivity {
    String curentDate;
    ArrayList<JadwalSolatModel> jadwalSolatModels=new ArrayList<>();
    ArrayList<String> kab=new ArrayList<>();
    JsonUtil jsonUtil=new JsonUtil();
    TextView subuh,duhur,ashar,magrib,isya;
    TextView jamSubuh,jamDuhur,jamAshar,jamMagrib,jamIsya;
    TextView tvTanggal,tvTempat;
    Spinner spinner;
    String textTempat;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_sholat);
        intialisasi();
        getCurentDate();
        progresDialog();
        progresDialog1();
        jsonUtil.getJadwalSholat(this,jadwalSolatModels,curentDate,"Banyumas",jamSubuh,jamDuhur,jamAshar,jamMagrib,jamIsya,progressDialog);
        jsonUtil.getKabupaten(this,kab,spinner,progressDialog1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textTempat=spinner.getSelectedItem().toString();
                tvTempat.setText(spinner.getSelectedItem().toString());
                jsonUtil.getJadwalSholat(JadwalSholatActivity.this,jadwalSolatModels,curentDate,textTempat,jamSubuh,jamDuhur,jamAshar,jamMagrib,jamIsya,progressDialog);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public static void setAutoOrientationEnabled(Context context, boolean enabled)
    {
        Settings.System.putInt( context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }
    private void getCurentDate(){
        Date c= Calendar.getInstance().getTime();
        Log.d("tanggal", "getCurentDate: "+c);
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        curentDate=dateFormat.format(c);
        Log.d("tanggalString", "getCurentDate: "+curentDate);

        Date tvTanggals=Calendar.getInstance().getTime();
        SimpleDateFormat df=new SimpleDateFormat("dd MMMM YYYY");
        Log.d("tanggalString", "getCurentDate: "+df.format(tvTanggals));
        tvTanggal.setText(df.format(tvTanggals));
    }
    private void intialisasi(){
        subuh=findViewById(R.id.jenis_waktu);
        duhur=findViewById(R.id.jenis_waktu1);
        ashar=findViewById(R.id.jenis_waktu2);
        magrib=findViewById(R.id.jenis_waktu3);
        isya=findViewById(R.id.jenis_waktu4);

        jamSubuh=findViewById(R.id.jadwal_solat);
        jamDuhur=findViewById(R.id.jadwal_solat1);
        jamAshar=findViewById(R.id.jadwal_solat2);
        jamMagrib=findViewById(R.id.jadwal_solat3);
        jamIsya=findViewById(R.id.jadwal_solat4);

        tvTanggal=findViewById(R.id.tv_tanggal);
        tvTempat=findViewById(R.id.tv_tempat);
        spinner=findViewById(R.id.spiner);
        //JadwalSolatModel jadwalSolatModel=new JadwalSolatModel();


    }
    private void progresDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Jadwal Solat");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    private void progresDialog1() {
        progressDialog1=new ProgressDialog(this);
        progressDialog1.setMessage("Sedang Mengambil Jadwal Solat");
        progressDialog1.setIndeterminate(false);
        progressDialog1.setCanceledOnTouchOutside(true);
        progressDialog1.setCancelable(true);
        progressDialog1.show();
    }


}
