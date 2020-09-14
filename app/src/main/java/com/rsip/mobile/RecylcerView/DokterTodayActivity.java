package com.rsip.mobile.RecylcerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsip.mobile.R;
import com.rsip.mobile.View.DetailDokterActivity;
import com.rsip.mobile.View.InfoSemuaDokterActivity;

import android.widget.Toast;
import android.os.Handler;


public class DokterTodayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private DokterTodayAdapter mAdapter;

    private ArrayList<DokterTodayModel> modelList = new ArrayList<>();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Dokter");
    String hasilHari="";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_today);

        findViews();

        getHariIni();
        readDokter();
        progresDialog();
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
    private void readDokter(){
        Date tanggalDanWaktu=new Date();
        Locale id = new Locale("in", "ID");
        String pola="EEEE";
        SimpleDateFormat format;
        if (id==null){
            format=new SimpleDateFormat(pola);
        }else{
            format=new SimpleDateFormat(pola,id);
        }
       String hasilHaris=format.format(tanggalDanWaktu);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DokterTodayModel dokterTodayModel=dataSnapshot.getValue(DokterTodayModel.class);
                    dokterTodayModel.setKey(dataSnapshot.getKey());
                    Log.d("Doktermodel", "onDataChange: "+dokterTodayModel.getHariPraktik());
                    String hariPraktik=dokterTodayModel.getHariPraktik();
                    String hariPraktikKedua= dokterTodayModel.getHariPraktikKedua();
                    if (hariPraktik.contains(hasilHaris)&&TextUtils.isEmpty(hariPraktikKedua)) {
                        modelList.add(dokterTodayModel);
                    }
                    if (!TextUtils.isEmpty(hariPraktikKedua)){
                        if (hariPraktik.contains(hasilHaris)||hariPraktikKedua.contains(hasilHaris)) {
                            modelList.add(dokterTodayModel);
                        }
                    }
                    progressDialog.dismiss();
                    setAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setAdapter() {
        mAdapter = new DokterTodayAdapter(DokterTodayActivity.this, modelList);

        recyclerView.setHasFixedSize(true);


        final GridLayoutManager layoutManager = new GridLayoutManager(DokterTodayActivity.this, 2);
        recyclerView.addItemDecoration(new GridMarginDecoration(DokterTodayActivity.this, 2, 2, 2, 2));
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new DokterTodayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, DokterTodayModel model) {

                //handle item click events here
                //Toast.makeText(DokterTodayActivity.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DokterTodayActivity.this, DetailDokterActivity.class);
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
                finish();

            }
        });


    }
    private void getHariIni(){
        Date tanggalDanWaktu=new Date();
        Locale id = new Locale("in", "ID");
        String pola="EEEE";
        SimpleDateFormat format;
        if (id==null){
            format=new SimpleDateFormat(pola);
        }else{
            format=new SimpleDateFormat(pola,id);
        }
        hasilHari=format.format(tanggalDanWaktu);
        Log.d("hari ini", "getHariIni: "+hasilHari);
    }

    private void progresDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Data Dokter");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

}
