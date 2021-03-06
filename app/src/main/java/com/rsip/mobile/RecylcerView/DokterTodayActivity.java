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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.rsip.mobile.Adapter.CustomDokterTodayAdapter;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.Model.CustomDokterTodayModel;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.Koneksi;
import com.rsip.mobile.View.DetailDokterActivity;
import com.rsip.mobile.View.InfoSemuaDokterActivity;
import com.rsip.mobile.View.ShareAllJadwalDokter;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DokterTodayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView recyclerViews;


    private DokterTodayAdapter mAdapter;
    private CustomDokterTodayAdapter mAdapters;
    TextView todayDate,shareJadwal,textTitle;

    private ArrayList<DokterTodayModel> modelList = new ArrayList<>();
    private ArrayList<CustomDokterTodayModel> modelLists = new ArrayList<>();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Dokter");
    String hasilHari="";
    ProgressDialog progressDialog;
    private Retrofit retrofit, retrofitUmum;
    private String curentDate;
    Intent intent;

    private LinearLayout linEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_today);

        findViews();

        getHariIni();
        //readDokter();
       // progresDialog();
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_umur);
        linEmptyView=findViewById(R.id.linEmptyView);
        linEmptyView.setVisibility(View.GONE);
        todayDate=findViewById(R.id.todayDate);
        textTitle=findViewById(R.id.text_title);
        shareJadwal=findViewById(R.id.tulisanBagikanJadwal);
        retrofit = new Retrofit.Builder()
                .baseUrl(Koneksi.URL_TAMPIL_JADWAL_POLI_UMUM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitUmum = new Retrofit.Builder()
                .baseUrl(Koneksi.URL_TAMPIL_JADWAL_ALL_POLI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        intent=getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("umum"))){
            getAllJadwal();
            todayDate.setVisibility(View.GONE);
            textTitle.setText("Dokter Umum");
        }else if (TextUtils.isEmpty(intent.getStringExtra("umum"))){
            getJadwal();
        }
        shareJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DokterTodayActivity.this, ShareAllJadwalDokter.class);
                intent.putExtra("dokterToday","true");
                startActivity(intent);
            }
        });

    }
//    private void readDokter(){
//        Date tanggalDanWaktu=new Date();
//        Locale id = new Locale("in", "ID");
//        String pola="EEEE";
//        SimpleDateFormat format;
//        if (id==null){
//            format=new SimpleDateFormat(pola);
//        }else{
//            format=new SimpleDateFormat(pola,id);
//        }
//       String hasilHaris=format.format(tanggalDanWaktu);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    DokterTodayModel dokterTodayModel=dataSnapshot.getValue(DokterTodayModel.class);
//                    dokterTodayModel.setKey(dataSnapshot.getKey());
//                    Log.d("Doktermodel", "onDataChange: "+dokterTodayModel.getHariPraktik());
//                    String hariPraktik=dokterTodayModel.getHariPraktik();
//                    String hariPraktikKedua= dokterTodayModel.getHariPraktikKedua();
//                    if (hariPraktik.contains(hasilHaris)&&TextUtils.isEmpty(hariPraktikKedua)) {
//                        modelList.add(dokterTodayModel);
//                    }
//                    if (!TextUtils.isEmpty(hariPraktikKedua)){
//                        if (hariPraktik.contains(hasilHaris)||hariPraktikKedua.contains(hasilHaris)) {
//                            modelList.add(dokterTodayModel);
//                        }
//                    }
//                    progressDialog.dismiss();
//                    setAdapter();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


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
                intent.putExtra("doktertoday","doktertoday");
                intent.putExtra("kdPoliklinik",model.getKd_poliklinikx());
                intent.putExtra("nm_poliklinikx",model.getNm_poliklinikx());
                intent.putExtra("nip_dokterx",model.getNip_dokterx());
                intent.putExtra("nm_dokterx",model.getNm_dokterx());
                intent.putExtra("harix",model.getHarix());
                intent.putExtra("tglx",model.getTglx());
                intent.putExtra("jam_mulaix",model.getJam_mulaix());
                intent.putExtra("jam_selesaix",model.getJam_selesaix());
                startActivity(intent);

            }
        });


    }

    private void CustomSetAdapter(){
        mAdapters = new CustomDokterTodayAdapter(DokterTodayActivity.this,modelLists);
        recyclerViews.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(DokterTodayActivity.this, 2);
        recyclerViews.addItemDecoration(new GridMarginDecoration(DokterTodayActivity.this, 2, 2, 2, 2));
        recyclerViews.setLayoutManager(layoutManager);


        recyclerViews.setAdapter(mAdapters);

        //mAdapters

    }
    private void getCurrentDate(){
        Date c= Calendar.getInstance().getTime();
        Log.d("tanggal", "getCurentDate: "+c);
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        curentDate=dateFormat.format(c);
        Log.d("tanggal", "getCurrentDate: "+curentDate);
    }
    private void getJadwal(){
        getCurrentDate();
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
        Log.d("hasilhari", "getJadwal: "+hasilHaris);
        String titleTodayDate=hasilHaris;
        titleTodayDate+=", "+curentDate;
        todayDate.setText(titleTodayDate);
        HashMap<String,String> param=new HashMap<>();
        //param.put("TANGGAL_PERIKSA","27/12/2020");
        param.put("TANGGAL_PERIKSA",curentDate);
        ApiService apiService=retrofit.create(ApiService.class);
        Call<JsonObject> result=apiService.postMessage(param);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    JSONObject res = jsonObject.getJSONObject("response");
                    JSONArray list = res.getJSONArray("list");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject data=list.getJSONObject(i);
                        DokterTodayModel todayModel=new DokterTodayModel();
                        todayModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                        todayModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                        todayModel.setNip_dokterx(data.getString("nip_dokterx"));
                        todayModel.setNm_dokterx(data.getString("nm_dokterx"));
                        todayModel.setHarix(data.getString("harix"));
                        todayModel.setTglx(data.getString("tglx"));
                        todayModel.setJam_mulaix(data.getString("jam_mulaix"));
                        todayModel.setJam_selesaix(data.getString("jam_selesaix"));

//                        if (!TextUtils.isEmpty(intent.getStringExtra("umum"))) {
//                            if (data.getString("kd_poliklinikx").equalsIgnoreCase("P.Umm")) {
////                                if (data.getString("harix").equalsIgnoreCase(hasilHaris)) {
//                                    modelList.add(todayModel);
//                               // }
//                            }
//                        }
                        if (TextUtils.isEmpty(intent.getStringExtra("umum"))) {
                            if (data.getString("harix").equalsIgnoreCase(hasilHaris)) {
                                modelList.add(todayModel);
                            }
                        }

                        setAdapter();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    if (modelList.size()==0){
                        recyclerView.setVisibility(View.GONE);
                        linEmptyView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

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
    private void getAllJadwal(){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Koneksi.URL_TAMPIL_JADWAL_ALL_POLI, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response.length()>0){

                    try{
                        JSONArray root=response.getJSONArray("response");
                        for (int i = 0; i <root.length() ; i++) {
                            //ArrayList<JadwalDokterAllModel> singleItemList,selasaItemList,rabuItemList,kamisItemList,jumatItemList,sabtuItemList = new ArrayList<>();
                            JSONObject data=root.getJSONObject(i);
                            //Log.d(TAG, "onResponse: ");

                            DokterTodayModel todayModel=new DokterTodayModel();
                            todayModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                            todayModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                            todayModel.setNip_dokterx(data.getString("nip_dokterx"));
                            todayModel.setNm_dokterx(data.getString("nm_dokterx"));
                            todayModel.setHarix(data.getString("harix"));
                            //todayModel.setTglx(data.getString("tglx"));
                            todayModel.setJam_mulaix(data.getString("jam_mulaix"));
                            todayModel.setJam_selesaix(data.getString("jam_selesaix"));

                            //dari intent dokter today
                            if (!TextUtils.isEmpty(intent.getStringExtra("umum"))) {
                                if (data.getString("kd_poliklinikx").equalsIgnoreCase("P.Umm")) {
//                                if (data.getString("harix").equalsIgnoreCase(hasilHaris)) {
                                    modelList.add(todayModel);
                                    // }
                                }
                            }


                        }


                        setAdapter();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }



//    private void progresDialog(){
//        progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Sedang Mengambil Data Dokter");
//        progressDialog.setIndeterminate(false);
//        progressDialog.setCanceledOnTouchOutside(true);
//        progressDialog.setCancelable(true);
//        progressDialog.show();
//    }

    }

