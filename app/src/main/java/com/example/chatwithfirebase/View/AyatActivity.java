package com.example.chatwithfirebase.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.chatwithfirebase.Adapter.AyatAdapter;
import com.example.chatwithfirebase.Model.AyatModel;
import com.example.chatwithfirebase.R;
import com.example.chatwithfirebase.Utils.JsonUtil;

import java.util.ArrayList;

public class AyatActivity extends AppCompatActivity {
    Intent intent;
    int idSurah;
    RecyclerView recyclerView;
    ArrayList<AyatModel> ayatModels=new ArrayList<>();
    AyatAdapter ayatAdapter;
    JsonUtil jsonUtil=new JsonUtil();
    ProgressDialog progressDialog;
    TextView textTitle,tvTypeSurah,tvTranslation,tvJumlahAyat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayat);
        recyclerView=findViewById(R.id.ayat_recylcer);
        textTitle=findViewById(R.id.text_title);
        tvTypeSurah=findViewById(R.id.tv_typesurah);
        tvTranslation=findViewById(R.id.tv_translation);
        tvJumlahAyat=findViewById(R.id.tv_jumlahayat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        intent=getIntent();
        idSurah=intent.getIntExtra("idposisi",2);
        toAdapter();
        progresDialog();
        jsonUtil.getAyat(this, ayatAdapter,ayatModels,progressDialog,idSurah,textTitle,tvTypeSurah,tvTranslation,tvJumlahAyat);
        jsonUtil.getAyat(this,ayatAdapter,ayatModels,idSurah);
      //  getData();
    }
    public void toAdapter(){
        ayatAdapter=new AyatAdapter(this,ayatModels);
        recyclerView.setAdapter(ayatAdapter);
    }
    private void progresDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Ayat");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    private void getData(){
        textTitle.setText(ayatModels.get(idSurah-1).getNamaSurah());
    }
}
