package com.rsip.mobile.RecylcerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsip.mobile.R;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.Handler;


public class HistoryPeriksaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private HistoriPeriksaAdapter mAdapter;

    private ArrayList<HistoryPeriksaModel> modelList = new ArrayList<>();
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference reference;
    RelativeLayout emptyView;
    Button btnGantiTanggal;
    String tahun,bulan,hari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_periksa);

        findViews();
        getDateToTanggalPeriksa();




    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        emptyView=findViewById(R.id.rel_emptyview);
        btnGantiTanggal=findViewById(R.id.btn_GantiTanggal);
        showDate();
    }


    private void setAdapter() {

        mAdapter = new HistoriPeriksaAdapter(HistoryPeriksaActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new HistoriPeriksaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, HistoryPeriksaModel model) {

                //handle item click events here
                Toast.makeText(HistoryPeriksaActivity.this, "Hey " + model.getDrTuju(), Toast.LENGTH_SHORT).show();


            }
        });


    }
    private void getDateToTanggalPeriksa(){
//        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
//        dateFormat.setTimeZone(TimeZone.getDefault());
//        Date today= Calendar.getInstance().getTime();
//        String todayDate=dateFormat.format(today);
//        Log.d("bookingdate", "getDateToTanggalPeriksa: "+todayDate);

        //final Calendar cldr=Calendar.getInstance();
        int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month=Calendar.getInstance().get(Calendar.MONTH);
        int year=Calendar.getInstance().get(Calendar.YEAR);

        String mYear=String.valueOf(year);
        String mMonth=String.valueOf(month+1);
        String mDay=String.valueOf(day);
        Log.d("history", "getDateToTanggalPeriksa: "+mYear+mMonth+mDay);
        reference=FirebaseDatabase.getInstance().getReference("Pendaftaran").child("BPJS").child(mYear).child(mMonth).child(mDay);
        readPendaftaran();
    }
    private void readPendaftaran(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    HistoryPeriksaModel historyPeriksaModel=dataSnapshot.getValue(HistoryPeriksaModel.class);
                    if (historyPeriksaModel.getIdSender().equals(firebaseUser.getUid())){
                        modelList.add(historyPeriksaModel);
                        emptyView.setVisibility(View.GONE);
                    }else {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    Log.d("History", "onDataChange: "+historyPeriksaModel.getDrTuju());
                    setAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDate(){
        btnGantiTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }
    private void showDatePicker(){
        final Calendar cldr=Calendar.getInstance();
        int day=cldr.get(Calendar.DAY_OF_MONTH);
        int month=cldr.get(Calendar.MONTH);
        int year=cldr.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                hari=String.valueOf(dayOfMonth);
                bulan=String.valueOf(month+1);
                tahun=String.valueOf(year);
            }
        },year,month,day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }


}
