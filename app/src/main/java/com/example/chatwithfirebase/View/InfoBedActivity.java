package com.example.chatwithfirebase.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.chatwithfirebase.Adapter.InfoBedAdapter;
import com.example.chatwithfirebase.Model.InfoBedModel;
import com.example.chatwithfirebase.R;
import com.example.chatwithfirebase.Utils.JsonUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InfoBedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<InfoBedModel> infoBedModels=new ArrayList<>();
    InfoBedAdapter infoBedAdapter;
    JsonUtil jsonUtils = new JsonUtil();
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bed);
        Log.d("info", "onCreate: masuk infobed");
        recyclerView=findViewById(R.id.recy_list_bed);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        progresDialog();
        recyclerView.setLayoutManager(layoutManager);
        toAdapter();
        jsonUtils.getInfoBed(this,infoBedAdapter,infoBedModels,progressDialog);

    }
    private void toAdapter(){
        infoBedAdapter=new InfoBedAdapter(this,infoBedModels);
        recyclerView.setAdapter(infoBedAdapter);
    }

    private void progresDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Tempat Tidur");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
