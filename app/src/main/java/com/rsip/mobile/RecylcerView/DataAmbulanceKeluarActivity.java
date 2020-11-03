package com.rsip.mobile.RecylcerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsip.mobile.R;
import com.rsip.mobile.View.DetailAmbulanceKeluarActivity;

import android.widget.Toast;
import android.os.Handler;


public class DataAmbulanceKeluarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private DataAmbulanceKeluarAdapter mAdapter;

    private ArrayList<DataAmbulanceKeluarModel> modelList = new ArrayList<>();

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Driver").child("MobilKeluar");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_ambulance_keluar);

        findViews();
        setAdapter();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        readFromFirebase();
    }


    private void setAdapter() {


        mAdapter = new DataAmbulanceKeluarAdapter(DataAmbulanceKeluarActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new DataAmbulanceKeluarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DataAmbulanceKeluarModel model) {

                //handle item click events here
                //Toast.makeText(DataAmbulanceKeluarActivity.this, "Hey " + model.getNoPlat(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DataAmbulanceKeluarActivity.this, DetailAmbulanceKeluarActivity.class);
                intent.putExtra("NoPlat",model.getNoPlat());
                intent.putExtra("driver",model.getDriver());
                intent.putExtra("jamInput",model.getJamInput());
                intent.putExtra("jamOtomatis",model.getJamOtomatis());
                intent.putExtra("jarak",model.getJarak());
                intent.putExtra("jenisKendaraan",model.getJenisKendaraan());
                intent.putExtra("merkKendaraan",model.getMerkKendaraan());
                intent.putExtra("tanggalInput",model.getTanggalInput());
                intent.putExtra("tanggalOtomatis",model.getTanggalOtomatis());
                intent.putExtra("tujuan",model.getTujuan());

                startActivity(intent);
                finish();


            }
        });


    }

    private void readFromFirebase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DataAmbulanceKeluarModel dataAmbulanceKeluarModel=dataSnapshot.getValue(DataAmbulanceKeluarModel.class);
                    dataAmbulanceKeluarModel.setKey(dataSnapshot.getKey());
                    if (dataAmbulanceKeluarModel.getStatus().equalsIgnoreCase("Keluar")){
                        modelList.add(dataAmbulanceKeluarModel);
                    }
                    setAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
