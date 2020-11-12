package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
    TextView day,month,year,date,holiday,dateMasehi;
    Button btnGantiTanggal,btnBagikanAyat,btnGantiAyat;
    JsonUtil jsonUtil=new JsonUtil();
    ProgressDialog progressDialog;
    TextView quoteText,quoteAuthor,surahNo,ayahNo;
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
        dateMasehi=findViewById(R.id.textviewDateMasehi);
        btnGantiTanggal=findViewById(R.id.btn_changeDateHijr);
        quoteAuthor=findViewById(R.id.textViewAuthor);
        quoteText=findViewById(R.id.textviewQuote);
        surahNo=findViewById(R.id.surahNo);
        ayahNo=findViewById(R.id.ayahNo);
        btnBagikanAyat=findViewById(R.id.btnBagikanAyat);
        btnGantiAyat=findViewById(R.id.btnGantiAyat);

        getDateNow();
        progresDialog();
        jsonUtil.getDateHijri(this,kalendarHijriModels,currentDate,day,month,year,date,holiday);
        jsonUtil.getQuotesIslamic(this,quoteText,quoteAuthor,surahNo,ayahNo,progressDialog);

        btnGantiTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnBagikanAyat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote();
            }
        });
        btnGantiAyat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonUtil.getQuotesIslamic(KalendarHijriActivity.this,quoteText,quoteAuthor,surahNo,ayahNo,progressDialog);
            }
        });
    }

    private void getDateNow(){
        Date c=Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        currentDate=dateFormat.format(c);
        dateMasehi.setText(currentDate);
    }

    private void progresDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Data");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    private void showDatePicker(){
        Calendar c=Calendar.getInstance();
        int days=c.get(Calendar.DAY_OF_MONTH);
        int months=c.get(Calendar.MONTH);
        int years=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearss, int monthss, int dayOfMonth) {
                currentDate=dayOfMonth+"-"+(monthss+1)+"-"+yearss;
                Log.d("hijricurentdate", "onDateSet: "+currentDate);
                dateMasehi.setText(currentDate);
                jsonUtil.getDateHijri(KalendarHijriActivity.this,kalendarHijriModels,currentDate,day,month,year,date,holiday);
            }
        },years,months,days);
        datePickerDialog.show();

    }

    private void shareQuote(){
        Intent intent=new Intent(KalendarHijriActivity.this,ShareQuoteActivity.class);
        intent.putExtra("quote",quoteText.getText().toString());
        intent.putExtra("author",quoteAuthor.getText().toString());
        intent.putExtra("surahno",surahNo.getText().toString());
        intent.putExtra("ayahno",ayahNo.getText().toString());
        startActivity(intent);

    }


}
