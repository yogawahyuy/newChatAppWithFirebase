package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

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
}
