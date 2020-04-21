package com.example.chatwithfirebase.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.chatwithfirebase.Adapter.InfoBedAdapter;
import com.example.chatwithfirebase.Model.InfoBedModel;
import com.example.chatwithfirebase.R;
import com.example.chatwithfirebase.Utils.JsonUtil;

import java.util.ArrayList;

public class InfoBedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<InfoBedModel> infoBedModels=new ArrayList<>();
    InfoBedAdapter infoBedAdapter;
    JsonUtil jsonUtils = new JsonUtil();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bed);
        Log.d("info", "onCreate: masuk infobed");
        recyclerView=findViewById(R.id.recy_list_bed);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        toAdapter();



    }
    private void toAdapter(){
        infoBedAdapter=new InfoBedAdapter(this,infoBedModels);
        recyclerView.setAdapter(infoBedAdapter);
        jsonUtils.getInfoBed(this,infoBedAdapter,infoBedModels);


    }
}
