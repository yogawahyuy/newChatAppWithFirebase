package com.rsip.mobile.RecylcerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.HttpHandler;
import com.rsip.mobile.Utils.Koneksi;
import com.rsip.mobile.View.RingkasanDaftarOnlineActivity;

import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PoliklinikActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private PoliKlinikAdapter mAdapter;
    TextView hariPoli,tanggalPoli;
    Intent intent;
    String tanggal="";
    private ArrayList<PoliklinikModel> modelList = new ArrayList<>();
    private Retrofit retrofit;
    private Retrofit retrofitDaftar;
    String str=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poliklinik);

        findViews();
        setAdapter();
        //sendDataBody();
        //sendPostRequest();
        //postRequest();
        //getData();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        hariPoli=findViewById(R.id.hari_poliklinik);
        tanggalPoli=findViewById(R.id.tanggal_poliklinik);
        intent=getIntent();
        initialiseRetrofit();
        tanggal=intent.getStringExtra("tanggal");
        tanggalPoli.setText(intent.getStringExtra("tanggal"));
        hariPoli.setText(intent.getStringExtra("hari"));
        postMessage(tanggal);

        String parseTime=intent.getStringExtra("tanggal");
        parseDate(parseTime);
    }


    private String parseDate(String time){
        String inputParse="dd/MM/yyyy";
        String outputParse="yyyy-MM-dd";

        SimpleDateFormat inputFormat=new SimpleDateFormat(inputParse);
        SimpleDateFormat outputFormat=new SimpleDateFormat(outputParse);

        Date date=null;
         str=null;

        try{
            date=inputFormat.parse(time);
            str=outputFormat.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        Log.d("dataTime", "parseDate: "+time);
        Log.d("dataTime", "parseDate: "+str);
        postMessageBPJS(str);
        return str;
    }

    private void setAdapter() {
        mAdapter = new PoliKlinikAdapter(PoliklinikActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new PoliKlinikAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, PoliklinikModel model) {

                //handle item click events here
                //Toast.makeText(PoliklinikActivity.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intenToRingkasan=new Intent(PoliklinikActivity.this, RingkasanDaftarOnlineActivity.class);
                Log.d("dataIntent", "onItemClick: "+intent.getStringExtra("asuransi"));
                if (intent.getStringExtra("asuransi").equalsIgnoreCase("BPJS")){
                    intenToRingkasan.putExtra("nomorkartu",intent.getStringExtra("nomorkartu"));
                    intenToRingkasan.putExtra("nik",intent.getStringExtra("nik"));
                    intenToRingkasan.putExtra("asuransi",intent.getStringExtra("asuransi"));
                    intenToRingkasan.putExtra("nohp",intent.getStringExtra("nohp"));
                    intenToRingkasan.putExtra("nomorreferensi",intent.getStringExtra("nomorreferensi"));
                    intenToRingkasan.putExtra("tanggal",str);
                    intenToRingkasan.putExtra("kdPoliklinik",model.getKd_poliklinikx());
                    intenToRingkasan.putExtra("nmPoliKlinik",model.getNm_poliklinikx());
                    intenToRingkasan.putExtra("jenisreferensi",intent.getIntExtra("jenisreferens",1));
                    intenToRingkasan.putExtra("jenisrequest",intent.getIntExtra("jenisrequest",2));
                    intenToRingkasan.putExtra("polieksekutif",intent.getIntExtra("polieksekutif",0));
                    intenToRingkasan.putExtra("harix",model.getHarix());
                    intenToRingkasan.putExtra("nm_dokterx",model.getNm_dokterx());
                    intenToRingkasan.putExtra("jam_mulaix",model.getJam_mulaix());
                    intenToRingkasan.putExtra("jam_selesaix",model.getJam_selesaix());
                    Log.d("data", "onItemClick: "+model.getHarix());
                    startActivity(intenToRingkasan);
                }else{
                    intenToRingkasan.putExtra("kdPoliklinik",model.getKd_poliklinikx());
                    intenToRingkasan.putExtra("nm_poliklinikx",model.getNm_poliklinikx());
                    intenToRingkasan.putExtra("nip_dokterx",model.getNip_dokterx());
                    intenToRingkasan.putExtra("nm_dokterx",model.getNm_dokterx());
                    intenToRingkasan.putExtra("harix",model.getHarix());
                    intenToRingkasan.putExtra("tglx",model.getTglx());
                    intenToRingkasan.putExtra("jam_mulaix",model.getJam_mulaix());
                    intenToRingkasan.putExtra("jam_selesaix",model.getJam_selesaix());
                    intenToRingkasan.putExtra("namanya",intent.getStringExtra("namanya"));
                    intenToRingkasan.putExtra("norm",intent.getStringExtra("norm"));
                    intenToRingkasan.putExtra("asuransi",intent.getStringExtra("asuransi"));
                    intenToRingkasan.putExtra("nohp",intent.getStringExtra("nohp"));
                    intenToRingkasan.putExtra("tanggal",intent.getStringExtra("tanggal"));
                    intenToRingkasan.putExtra("hari",intent.getStringExtra("hari"));
                    startActivity(intenToRingkasan);
                }



            }
        });


    }


    private void initialiseRetrofit() {
        String asuransi=intent.getStringExtra("asuransi");
        if (asuransi.equalsIgnoreCase("BPJS")) {
            retrofit=new Retrofit.Builder()
                    .baseUrl(Koneksi.URL_TAMPIL_JADWAL_POLI_BPJS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Koneksi.URL_TAMPIL_JADWAL_POLI_UMUM)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    private void postMessageBPJS(String tanggal){
        HashMap<String,String> param=new HashMap<>();
        param.put("TANGGAL_PERIKSA",tanggal);
        //param.put("TANGGAL_PERIKSA","30/12/2020");
        ApiService apiService=retrofit.create(ApiService.class);
        Call<JsonObject> result=apiService.postMessage(param);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    //Toast.makeText(PoliklinikActivity.this, "respone message"+response.body().string(), Toast.LENGTH_SHORT).show();
                    Log.d("data", "onResponse: "+response.body().toString());
                    //String jadwal=response.body().string();
                    JSONObject jsonObject=new JSONObject(response.body().toString());
                    JSONObject res=jsonObject.getJSONObject("response");
                    JSONArray list=res.getJSONArray("list");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject data=list.getJSONObject(i);
                        PoliklinikModel poliklinikModel=new PoliklinikModel();
                        poliklinikModel.setKd_poliklinikx(data.getString("kd_poli_bpjsx"));
                        poliklinikModel.setNm_poliklinikx(data.getString("nm_poli_bpjsx"));
                        poliklinikModel.setNip_dokterx(data.getString("nip_dokterx"));
                        poliklinikModel.setNm_dokterx(data.getString("nm_dokterx"));
                        poliklinikModel.setHarix(data.getString("harix"));
                        poliklinikModel.setTglx(data.getString("tglx"));
                        poliklinikModel.setJam_mulaix(data.getString("jam_mulaix"));
                        poliklinikModel.setJam_selesaix(data.getString("jam_selesaix"));
                        modelList.add(poliklinikModel);
                    }
                    setAdapter();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void postMessage(String tanggal){
        HashMap<String,String> param=new HashMap<>();
        param.put("TANGGAL_PERIKSA",tanggal);
        //param.put("TANGGAL_PERIKSA","30/12/2020");
        ApiService apiService=retrofit.create(ApiService.class);
        Call<JsonObject> result=apiService.postMessage(param);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    //Toast.makeText(PoliklinikActivity.this, "respone message"+response.body().string(), Toast.LENGTH_SHORT).show();
                    Log.d("data", "onResponse: "+response.body().toString());
                    //String jadwal=response.body().string();
                    JSONObject jsonObject=new JSONObject(response.body().toString());
                    JSONObject res=jsonObject.getJSONObject("response");
                    JSONArray list=res.getJSONArray("list");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject data=list.getJSONObject(i);
                        PoliklinikModel poliklinikModel=new PoliklinikModel();
                        poliklinikModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                        poliklinikModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                        poliklinikModel.setNip_dokterx(data.getString("nip_dokterx"));
                        poliklinikModel.setNm_dokterx(data.getString("nm_dokterx"));
                        poliklinikModel.setHarix(data.getString("harix"));
                        poliklinikModel.setTglx(data.getString("tglx"));
                        poliklinikModel.setJam_mulaix(data.getString("jam_mulaix"));
                        poliklinikModel.setJam_selesaix(data.getString("jam_selesaix"));
                        modelList.add(poliklinikModel);
                    }
                    setAdapter();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


}
