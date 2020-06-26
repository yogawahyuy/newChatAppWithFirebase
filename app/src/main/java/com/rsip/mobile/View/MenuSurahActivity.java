package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rsip.mobile.Adapter.MenuSurahAdapter;
import com.rsip.mobile.Model.MenuSurahModel;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.JsonUtil;

import java.util.ArrayList;

public class MenuSurahActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MenuSurahModel> surahModels=new ArrayList<>();
    MenuSurahAdapter surahAdapter;
    JsonUtil jsonUtil=new JsonUtil();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_surah);
        recyclerView=findViewById(R.id.surah_recylcer);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        progresDialog();
        recyclerView.setLayoutManager(layoutManager);
        toAdapter();
        jsonUtil.getSurah(this,surahAdapter,surahModels,progressDialog);

    }

    private void toAdapter() {
        surahAdapter=new MenuSurahAdapter(this,surahModels);
        recyclerView.setAdapter(surahAdapter);
    }

    private void progresDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Surah");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    private String millisSecondToTimer(long millisecond){
        String timerString="";
        String secondsString;

        int hours=(int)(millisecond / (1000*60*60));
        int minutes=(int)(millisecond % (1000*60*60)) / (1000*60);
        int seconds=(int) ((millisecond % (1000*60*60)) % (1000*60) / 1000);

        if (hours>0){
            timerString=hours+":";
        }
        if (seconds<10){
            secondsString="0"+seconds;
        }else {
            secondsString=""+seconds;
        }

        timerString=timerString + minutes + ":" +secondsString;

        return timerString;
    }
}
