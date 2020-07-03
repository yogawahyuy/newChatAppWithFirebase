package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.rsip.mobile.Model.KalendarHijriModel;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.JsonUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KalendarHijriActivity extends AppCompatActivity {
    String currentDate;
    TextView day,month,year,date,holiday;
    JsonUtil jsonUtil=new JsonUtil();
    ProgressDialog progressDialog;
    ArrayList<KalendarHijriModel> kalendarHijriModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalendar_hijri);
        day=findViewById(R.id.part_day);
        month=findViewById(R.id.part_month);
        year=findViewById(R.id.part_year);
        date=findViewById(R.id.textviewDate);
        holiday=findViewById(R.id.textviewLiburan);
        getDateNow();
        progresDialog();
        jsonUtil.getDateHijri(this,kalendarHijriModels,currentDate,progressDialog,day,month,year,date,holiday);
    }

    private void getDateNow(){
        Date c=Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        currentDate=dateFormat.format(c);
    }

    private void progresDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Jadwal Solat");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
