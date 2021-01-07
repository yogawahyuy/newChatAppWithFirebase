package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import java.util.HashMap;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonObject;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.PoliklinikModel;
import com.rsip.mobile.Utils.JsonUtil;
import com.rsip.mobile.Utils.Koneksi;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import android.widget.AdapterView;
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


public class InfoSemuaDokterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private SemuaDokterAdapter mAdapter;

    private ArrayList<SemuaDokterModel> modelList = new ArrayList<>();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Dokter");
    StorageReference storageReference;
    Intent intent;
    private Retrofit retrofit;
    private String curentDate;
    private SearchableSpinner searchableSpinnerHari;
    private TextView tanggalDokter,bagikanJadwal,textViewEmpty;
    private RelativeLayout emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_semua_dokter);

        findViews();


    }

    private void findViews() {
        intent=getIntent();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchableSpinnerHari=findViewById(R.id.spinner_Hari);
        tanggalDokter=findViewById(R.id.text_tanggal);
        emptyView=findViewById(R.id.emptyview);
        textViewEmpty=findViewById(R.id.text_view_empty);
        String tanggalnya="Tanggal ";
        tanggalnya += intent.getStringExtra("TANGGAL_PERIKSA");
        tanggalDokter.setText(tanggalnya);
        bagikanJadwal=findViewById(R.id.tulisanBagikanJadwal);
        emptyView.setVisibility(View.GONE);
        initialitatonRetrofit();
        bagikanJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents=new Intent(InfoSemuaDokterActivity.this,ShareAllJadwalDokter.class);
                intents.putExtra("alldokter","true");
                intents.putExtra("TANGGAL_PERIKSA",intent.getStringExtra("TANGGAL_PERIKSA"));
                startActivity(intents);
            }
        });

       // postMessage("28/07/2020");
        //getJadwalAllPoli();
//        searchableSpinnerHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //getJadwalAllPoli();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        getJadwalDokter();

    }
    private void initialitatonRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Koneksi.URL_TAMPIL_JADWAL_POLI_UMUM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
//    private void readDokter(){
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                SemuaDokterModel dokterModel=new SemuaDokterModel();
//                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    dokterModel=dataSnapshot.getValue(SemuaDokterModel.class);
//                    dokterModel.setKey(dataSnapshot.getKey());
//                    modelList.add(dokterModel);
//
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
        mAdapter = new SemuaDokterAdapter(InfoSemuaDokterActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(InfoSemuaDokterActivity.this, R.drawable.divider_recyclerview));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(mAdapter);


        Log.d("ukuran modelis", "setAdapter: "+modelList.size());


        mAdapter.SetOnItemClickListener(new SemuaDokterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, SemuaDokterModel model) {

                //handle item click events here
                //Toast.makeText(InfoSemuaDokterActivity.this, "Hey " + model.getNamaDokter(), Toast.LENGTH_SHORT).show();

               Intent intent=new Intent(InfoSemuaDokterActivity.this,DetailDokterActivity.class);
                intent.putExtra("kdPoliklinik",model.getKd_poliklinikx());
                intent.putExtra("nm_poliklinikx",model.getNm_poliklinikx());
                intent.putExtra("nip_dokterx",model.getNip_dokterx());
                intent.putExtra("nm_dokterx",model.getNm_dokterx());
                intent.putExtra("harix",model.getHarix());
                intent.putExtra("tglx",model.getTglx());
                intent.putExtra("jam_mulaix",model.getJam_mulaix());
                intent.putExtra("jam_selesaix",model.getJam_selesaix());
               startActivity(intent);
                Log.d("detail", "onItemClick: "+modelList.get(position).getNm_dokterx());

            }
        });


    }
//    private void getSelectedItem(){
//        searchableSpinnerHari.getSelectedItem().
//    }

//    private void getCurrentDate(){
//        Date c= Calendar.getInstance().getTime();
//        Log.d("tanggal", "getCurentDate: "+c);
//        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
//        curentDate=dateFormat.format(c);
//        Log.d("tanggal", "getCurrentDate: "+curentDate);
//    }
//    private void postMessage(String tanggal){
//        getCurrentDate();
//        HashMap<String,String> param=new HashMap<>();
//        //param.put("TANGGAL_PERIKSA",tanggal);
//        param.put("TANGGAL_PERIKSA",curentDate);
//        ApiService apiService=retrofit.create(ApiService.class);
//        Call<JsonObject> result=apiService.postMessage(param);
//        result.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    Log.d("data", "onResponse: "+response.body().toString());
//                    JSONObject jsonObject=new JSONObject(response.body().toString());
//                    JSONObject res=jsonObject.getJSONObject("response");
//                    JSONArray list=res.getJSONArray("list");
//                    for (int i = 0; i <list.length() ; i++) {
//                        JSONObject data=list.getJSONObject(i);
//                        SemuaDokterModel semuaDokterModel=new SemuaDokterModel();
//                        semuaDokterModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
//                        semuaDokterModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
//                        semuaDokterModel.setNip_dokterx(data.getString("nip_dokterx"));
//                        semuaDokterModel.setNm_dokterx(data.getString("nm_dokterx"));
//                        semuaDokterModel.setHarix(data.getString("harix"));
//                        semuaDokterModel.setTglx(data.getString("tglx"));
//                        semuaDokterModel.setJam_mulaix(data.getString("jam_mulaix"));
//                        semuaDokterModel.setJam_selesaix(data.getString("jam_selesaix"));
//                        modelList.add(semuaDokterModel);
//                    }
//                    setAdapter();
//                } catch ( JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });
//
//    }

//    private void getJadwalAllPoli(){
//        setAdapter();
//        JsonUtil jsonUtil=new JsonUtil();
//        jsonUtil.getJadwalAllPoli(this,mAdapter,modelList,searchableSpinnerHari.getSelectedItem().toString());
//    }

    private void sendDataBagikanJadwal(){
        Intent intents=new Intent(InfoSemuaDokterActivity.this,ShareAllJadwalDokter.class);
        intents.putExtra("alldokter","true");
        intents.putExtra("TANGGAL_PERIKSA","TANGGAL_PERIKSA");
        startActivity(intent);

    }
    private void getJadwalDokter(){
        HashMap<String , String > param=new HashMap<>();
        param.put("TANGGAL_PERIKSA",intent.getStringExtra("TANGGAL_PERIKSA"));
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
                        SemuaDokterModel semuaDokterModel=new SemuaDokterModel();
                        semuaDokterModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                        semuaDokterModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                        semuaDokterModel.setNip_dokterx(data.getString("nip_dokterx"));
                        semuaDokterModel.setNm_dokterx(data.getString("nm_dokterx"));
                        semuaDokterModel.setHarix(data.getString("harix"));
                        semuaDokterModel.setTglx(data.getString("tglx"));
                        semuaDokterModel.setJam_mulaix(data.getString("jam_mulaix"));
                        semuaDokterModel.setJam_selesaix(data.getString("jam_selesaix"));
                        modelList.add(semuaDokterModel);
                        Log.d("ukuran modelis", "onResponse: "+modelList.size());

                    }


                    setAdapter();
                }catch (JSONException e){
                    e.printStackTrace();
                    if (modelList.size()==0){
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        textViewEmpty.setText("Tidak Ada Jadwal Dokter Hari Ini");
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


}
