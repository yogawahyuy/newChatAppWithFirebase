package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rsip.mobile.R;

import android.widget.Toast;
import android.os.Handler;


public class InfoSemuaDokterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private SemuaDokterAdapter mAdapter;

    private ArrayList<SemuaDokterModel> modelList = new ArrayList<>();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Dokter");
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_semua_dokter);

        findViews();
        readDokter();
        //setAdapter();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
    private void readDokter(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SemuaDokterModel dokterModel=new SemuaDokterModel();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dokterModel=dataSnapshot.getValue(SemuaDokterModel.class);
                    dokterModel.setKey(dataSnapshot.getKey());
                    modelList.add(dokterModel);

                    setAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter() {




        mAdapter = new SemuaDokterAdapter(InfoSemuaDokterActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(InfoSemuaDokterActivity.this, R.drawable.divider_recyclerview));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new SemuaDokterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, SemuaDokterModel model) {

                //handle item click events here
                //Toast.makeText(InfoSemuaDokterActivity.this, "Hey " + model.getNamaDokter(), Toast.LENGTH_SHORT).show();

               Intent intent=new Intent(InfoSemuaDokterActivity.this,DetailDokterActivity.class);
               intent.putExtra("key",modelList.get(position).getKey());
               intent.putExtra("id",modelList.get(position).getIdDokter());
               intent.putExtra("namadokter",modelList.get(position).getNamaDokter());
               intent.putExtra("spesial",modelList.get(position).getSpesialistik());
               intent.putExtra("hari",modelList.get(position).getHariPraktik());
               intent.putExtra("jam",modelList.get(position).getJamPraktik());
               intent.putExtra("status",modelList.get(position).getStatus());
               intent.putExtra("ket",modelList.get(position).getKeterangan());
               intent.putExtra("harikedua",modelList.get(position).getHariPraktikKedua());
               intent.putExtra("jamkedua",modelList.get(position).getJamPraktikKedua());
               startActivity(intent);
                Log.d("detail", "onItemClick: "+modelList.get(position).getIdDokter());

            }
        });


    }


}
