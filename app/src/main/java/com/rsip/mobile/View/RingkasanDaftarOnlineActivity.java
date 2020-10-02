package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.Interface.RegUmumApiService;
import com.rsip.mobile.MainActivity;
import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.PoliklinikActivity;
import com.rsip.mobile.Utils.Koneksi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RingkasanDaftarOnlineActivity extends AppCompatActivity {

    TextView noRM,namaPasien,poli,nmDokter,hariTanggal,jamPraktek,asuransi;
    Button btnDaftar,btnGantiTanggal,btnGantiPoli;
    Intent intent;
    private Retrofit retrofitDaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringkasan_daftar_online);
        findViews();
    }
    private void findViews(){
        noRM=findViewById(R.id.textnoRM);
        namaPasien=findViewById(R.id.textnama);
        poli=findViewById(R.id.textPoli);
        nmDokter=findViewById(R.id.textnamaDokter);
        hariTanggal=findViewById(R.id.textHariTanggal);
        jamPraktek=findViewById(R.id.textjam);
        btnDaftar=findViewById(R.id.btn_Daftar);
        btnGantiTanggal=findViewById(R.id.btn_GantiTanggal);
        btnGantiPoli=findViewById(R.id.btn_gantipoli);
        asuransi=findViewById(R.id.textpembayaran);
        intent=getIntent();
        fillFromIntent();
        initialiseRetrofitDaftar();

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMessageDaftar();
            }
        });
        btnGantiPoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RingkasanDaftarOnlineActivity.this,PoliklinikActivity.class));
            }
        });
        btnGantiTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RingkasanDaftarOnlineActivity.this,DaftarOnlineActivity.class));
            }
        });
    }

    private void fillFromIntent(){
        String tanggalHari=intent.getStringExtra("hari");
        tanggalHari +=" "+intent.getStringExtra("tanggal");

        String jammulaiselesai=intent.getStringExtra("jam_mulaix");
        jammulaiselesai+=" - "+intent.getStringExtra("jam_selesaix");
        noRM.setText(intent.getStringExtra("norm"));
        namaPasien.setText(intent.getStringExtra("namanya"));
        poli.setText(intent.getStringExtra("nm_poliklinikx"));
        nmDokter.setText(intent.getStringExtra("nm_dokterx"));
        hariTanggal.setText(tanggalHari);
        jamPraktek.setText(jammulaiselesai);
        asuransi.setText(intent.getStringExtra("asuransi"));
    }

    private void initialiseRetrofitDaftar(){
//        Gson gson=new GsonBuilder()
//                .setLenient().create();
        retrofitDaftar=new Retrofit.Builder()
                .baseUrl(Koneksi.URL_REG_PASIEN_UMUM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    private void postMessageDaftar(){
        HashMap<String ,String > hashMap=new HashMap<>();
        hashMap.put("KD_REKAM_MEDIS","977829");
        hashMap.put("KD_POLIKLINIK",intent.getStringExtra("kdPoliklinik"));
        hashMap.put("NIP_DOKTER",intent.getStringExtra("nip_dokterx"));
        // hashMap.put("TANGGAL_PERIKSA",intent.getStringExtra("tanggal"));
        hashMap.put("TANGGAL_PERIKSA","31/12/2020");
        RegUmumApiService apiService=retrofitDaftar.create(RegUmumApiService.class);
        Call<JsonObject> result=apiService.postMessageDaftar(hashMap);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(RingkasanDaftarOnlineActivity.this, "Berhasil Daftar", Toast.LENGTH_SHORT).show();
                Log.d("data", "onResponse: "+response.body().toString());
                startActivity(new Intent(RingkasanDaftarOnlineActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RingkasanDaftarOnlineActivity.this, "Gagal Daftar", Toast.LENGTH_SHORT).show();
                Log.d("data", "onFailure: "+t.getMessage());
                Log.d("data", "onFailure: "+call.toString());
            }
        });
    }

}