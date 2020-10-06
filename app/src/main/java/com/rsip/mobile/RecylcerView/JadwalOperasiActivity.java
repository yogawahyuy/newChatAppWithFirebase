package com.rsip.mobile.RecylcerView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import com.rsip.mobile.R;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.os.Handler;


public class JadwalOperasiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    Button btnTanggalMulai,btnTanggalAkhir;
    private JadwalOperasiAdapter mAdapter;

    private ArrayList<JadwalOperasiModel> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_operasi);

        findViews();
        setAdapter();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnTanggalMulai=findViewById(R.id.btn_tanggalAwal);
        btnTanggalAkhir=findViewById(R.id.btn_tanggalAkhir);
    }

    private void showtanggalMulai(){
        Calendar cldr=Calendar.getInstance();
        int day=cldr.get(Calendar.DAY_OF_MONTH);
        int month=cldr.get(Calendar.MONTH);
        int year=cldr.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        },year,month,day);

    }


    private void setAdapter() {


       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","28/07/2020 24.00"));
       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","Jumat"));
       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","Jumat"));
       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","Jumat"));
       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","Jumat"));


        mAdapter = new JadwalOperasiAdapter(JadwalOperasiActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new JadwalOperasiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, JadwalOperasiModel model) {

                //handle item click events here
                Toast.makeText(JadwalOperasiActivity.this, "Hey " + model.getOperator(), Toast.LENGTH_SHORT).show();


            }
        });


    }


}
