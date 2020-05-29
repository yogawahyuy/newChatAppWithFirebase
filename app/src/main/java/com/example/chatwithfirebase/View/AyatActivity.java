package com.example.chatwithfirebase.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatwithfirebase.Model.AyatModel;
import com.example.chatwithfirebase.R;

import java.util.ArrayList;

public class AyatActivity extends AppCompatActivity {
    Intent intent;
    int idSurah;
    RecyclerView recyclerView;
    ArrayList<AyatModel> ayatModels=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayat);
        intent=getIntent();
        idSurah=intent.getIntExtra("idposisi",1);

    }
}
