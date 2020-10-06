package com.rsip.mobile.RecylcerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.Koneksi;

import android.widget.Toast;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RiwayatDaftarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private RiwayatDaftarAdapter mAdapter;

    Intent intent;
    Retrofit retrofit;

    private ArrayList<RiwayatDaftarModel> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_daftar);

        findViews();
        setAdapter();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        intent=getIntent();
        initialitatonRetrofit();
        postMessageDaftarAntrian();
    }


    private void setAdapter() {


//        modelList.add(new RiwayatDaftarModel("Android", "Hello " + " Android"));
//        modelList.add(new RiwayatDaftarModel("Beta", "Hello " + " Beta"));
//        modelList.add(new RiwayatDaftarModel("Cupcake", "Hello " + " Cupcake"));
//        modelList.add(new RiwayatDaftarModel("Donut", "Hello " + " Donut"));
//        modelList.add(new RiwayatDaftarModel("Eclair", "Hello " + " Eclair"));
//        modelList.add(new RiwayatDaftarModel("Froyo", "Hello " + " Froyo"));
//        modelList.add(new RiwayatDaftarModel("Gingerbread", "Hello " + " Gingerbread"));
//        modelList.add(new RiwayatDaftarModel("Honeycomb", "Hello " + " Honeycomb"));
//        modelList.add(new RiwayatDaftarModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
//        modelList.add(new RiwayatDaftarModel("Jelly Bean", "Hello " + " Jelly Bean"));
//        modelList.add(new RiwayatDaftarModel("KitKat", "Hello " + " KitKat"));
//        modelList.add(new RiwayatDaftarModel("Lollipop", "Hello " + " Lollipop"));
//        modelList.add(new RiwayatDaftarModel("Marshmallow", "Hello " + " Marshmallow"));
//        modelList.add(new RiwayatDaftarModel("Nougat", "Hello " + " Nougat"));
//        modelList.add(new RiwayatDaftarModel("Android O", "Hello " + " Android O"));


        mAdapter = new RiwayatDaftarAdapter(RiwayatDaftarActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new RiwayatDaftarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RiwayatDaftarModel model) {

                //handle item click events here
                //Toast.makeText(RiwayatDaftarActivity.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void initialitatonRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Koneksi.URL_RESPON_DAFTAR_ANTRIAN_POLI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    private void postMessageDaftarAntrian(){
        HashMap<String,String> param=new HashMap<>();
        param.put("KD_REKAM_MEDIS",intent.getStringExtra("KD_REKAM_MEDIS"));
        param.put("TANGGAL_PERIKSA",intent.getStringExtra("TANGGAL_PERIKSA"));
        ApiService apiService= retrofit.create(ApiService.class);
        Call<JsonObject> result=apiService.postBatalDaftar(param);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try{
                    JSONObject root=new JSONObject(response.body().toString());
                    JSONObject respon=root.getJSONObject("response");
                    JSONArray list=respon.getJSONArray("list");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject data=list.getJSONObject(i);
                        RiwayatDaftarModel riwayatDaftarModel=new RiwayatDaftarModel();
                        riwayatDaftarModel.setNomorantrean(data.getString("nomorantrean"));
                        riwayatDaftarModel.setKodebooking(data.getString("kodebooking"));
                        riwayatDaftarModel.setNamapasien(data.getString("namapasien"));
                        riwayatDaftarModel.setEstimasidilayani(data.getString("estimasidilayani"));
                        riwayatDaftarModel.setJamdilayani(data.getString("jamdilayani"));
                        riwayatDaftarModel.setNamapoli(data.getString("namapoli"));
                        riwayatDaftarModel.setNamadokter(data.getString("namadokter"));
                        riwayatDaftarModel.setHari(data.getString("hari"));
                        riwayatDaftarModel.setTanggal(data.getString("tanggal"));
                        riwayatDaftarModel.setJam_mulai(data.getString("jam_mulai"));
                        riwayatDaftarModel.setJam_selesai(data.getString("jam_selesai"));
                        modelList.add(riwayatDaftarModel);

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
