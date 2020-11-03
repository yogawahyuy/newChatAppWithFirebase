package com.rsip.mobile.RecylcerView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.JsonObject;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.ApiHandler;
import com.rsip.mobile.Utils.Koneksi;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JadwalOperasiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    Button btnTanggalMulai,btnTanggalAkhir;
    TextView noKartu;
    private JadwalOperasiAdapter mAdapter;

    private ArrayList<JadwalOperasiModel> modelList = new ArrayList<>();

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_operasi);

        findViews();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnTanggalMulai=findViewById(R.id.btn_tanggalAwal);
        btnTanggalAkhir=findViewById(R.id.btn_tanggalAkhir);
        noKartu=findViewById(R.id.textView_nokartu);
        intent=getIntent();
        noKartu.setText(intent.getStringExtra("nopeserta"));
        getData();
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


//       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","28/07/2020 24.00"));
//       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","Jumat"));
//       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","Jumat"));
//       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","Jumat"));
//       modelList.add(new JadwalOperasiModel("123456","Kamar 1","22/12/2020","15.00 - 18.00","S20","12","dr. Yoga Wahyu Yuwono Sp.com","Spesialis Komputer","Jumat"));


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
                //Toast.makeText(JadwalOperasiActivity.this, "Hey " + model.getOperator(), Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void getData(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("nopeserta",intent.getStringExtra("nopeserta"));

        ApiService apiService= ApiHandler.createServiceJadwal(ApiService.class, Koneksi.URL_JADWAL_OPERASI_BPJS);
        Call<JsonObject> call=apiService.postJSONOperasi(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {

                    JSONObject root=new JSONObject(response.body().toString());
                    JSONObject respon=root.getJSONObject("response");
                    JSONArray list=respon.getJSONArray("list");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject data=list.getJSONObject(i);
                        JadwalOperasiModel jadwalOperasiModel=new JadwalOperasiModel();
                        jadwalOperasiModel.setKodebooking(data.getString("kodebooking"));
                        jadwalOperasiModel.setTanggaloperasi(data.getString("tanggaloperasi"));
                        jadwalOperasiModel.setJenistindakan(data.getString("jenistindakan"));
                        jadwalOperasiModel.setKodepoli(data.getString("kodepoli"));
                        jadwalOperasiModel.setNamapoli(data.getString("namapoli"));
                        String terlaksana=data.getString("terlaksana");

                        if (terlaksana.equalsIgnoreCase("0")){
                            jadwalOperasiModel.setTerlaksana("Belum Dioperasi");
                        }else{
                            jadwalOperasiModel.setTerlaksana("Sudah Dioperasi");
                        }
                        modelList.add(jadwalOperasiModel);
                    }
                    setAdapter();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


}
