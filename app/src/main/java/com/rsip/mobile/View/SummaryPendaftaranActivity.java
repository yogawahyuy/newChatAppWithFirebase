package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rsip.mobile.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SummaryPendaftaranActivity extends AppCompatActivity {

    TextView antrianText,hariText,tanggalJamHari,poliSummary;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_pendaftaran);
        findView();

    }

    private void findView(){
        intent=getIntent();
        antrianText=findViewById(R.id.noAntrianText);
        hariText=findViewById(R.id.textharisumary);
        tanggalJamHari=findViewById(R.id.tanggaljam_sumary);
        poliSummary=findViewById(R.id.polisumary);
        fillIntent();
    }

    private void fillIntent(){
        if (intent.getStringExtra("asuransi").equalsIgnoreCase("BPJS")){
            antrianText.setText(intent.getStringExtra("nomorantrean"));
            String timeStamp= intent.getStringExtra("estimasidilayani");
            Log.d("data", "fillIntent: "+timeStamp);
            long estimasiDilayani=Long.parseLong(timeStamp) *1000L;
            tanggalJamHari.setText(getDate(estimasiDilayani));
            hariText.setVisibility(View.GONE);
            poliSummary.setText(intent.getStringExtra("namapoli"));
        }else{
            antrianText.setText(intent.getStringExtra("nomorantrean"));
            hariText.setText(intent.getStringExtra("hari"));
            tanggalJamHari.setText(intent.getStringExtra("jamdilayani"));
            poliSummary.setText(intent.getStringExtra("namapoli"));
        }
    }

    private String getDate(long timestamp){
        try{
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
//            Date netDate = (new Date(timestamp));
            Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
            calendar.setTimeInMillis(timestamp*1000L);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date date=calendar.getTime();


           // return DateFormat.format("dd/MM/yyyy hh:mm a",calendar);
            return simpleDateFormat.format(date);
        }catch(Exception ex){
            return "xx";
        }
    }
}