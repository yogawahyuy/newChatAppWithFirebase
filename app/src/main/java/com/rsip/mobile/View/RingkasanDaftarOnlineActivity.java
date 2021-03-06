package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.Interface.RegUmumApiService;
import com.rsip.mobile.MainActivity;
import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.PoliklinikActivity;
import com.rsip.mobile.Utils.ApiHandler;
import com.rsip.mobile.Utils.Koneksi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RingkasanDaftarOnlineActivity extends AppCompatActivity {

    TextView noRM,namaPasien,poli,nmDokter,hariTanggal,jamPraktek,asuransi,titleRM,titleNama,titleNamaDokter;
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
        titleRM=findViewById(R.id.titelRM);
        titleNama=findViewById(R.id.titelnama);
        titleNamaDokter=findViewById(R.id.titelnamaDokter);
        intent=getIntent();
        fillFromIntent();
        initialiseRetrofitDaftar();

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getStringExtra("asuransi").equalsIgnoreCase("BPJS")){
                    postMessageDaftarBPJS(v);
                }if (intent.getStringExtra("asuransi").equalsIgnoreCase("UMUM")){
                    postMessageDaftar();
                }
                //startActivity(new Intent(RingkasanDaftarOnlineActivity.this,SummaryPendaftaranActivity.class));
            }
        });
        btnGantiPoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(RingkasanDaftarOnlineActivity.this,PoliklinikActivity.class);
                intent1.putExtra("asuransi",intent.getStringExtra("asuransi"));
                startActivity(intent1);
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
        if (intent.getStringExtra("asuransi").equalsIgnoreCase("BPJS")){
            titleRM.setText("No Kartu BPJS");
            noRM.setText(intent.getStringExtra("nomorkartu"));
            titleNama.setText("No Rujukan");
            namaPasien.setText(intent.getStringExtra("nomorreferensi"));
            nmDokter.setText(intent.getStringExtra("nm_dokterx"));
            poli.setText(intent.getStringExtra("nmPoliKlinik"));
            asuransi.setText(intent.getStringExtra("asuransi"));
            String tanggalHari = intent.getStringExtra("harix");
            tanggalHari += " " + intent.getStringExtra("tanggal");
            String jammulaiselesai = intent.getStringExtra("jam_mulaix");
            jammulaiselesai += " - " + intent.getStringExtra("jam_selesaix");
            hariTanggal.setText(tanggalHari);
            jamPraktek.setText(jammulaiselesai);
            Log.d("dataRingkasan", "fillFromIntent: "+ intent.getStringExtra("harix"));
        }
        else {
            String tanggalHari = intent.getStringExtra("hari");
            tanggalHari += " " + intent.getStringExtra("tanggal");
            String jammulaiselesai = intent.getStringExtra("jam_mulaix");
            jammulaiselesai += " - " + intent.getStringExtra("jam_selesaix");
            noRM.setText(intent.getStringExtra("norm"));
            namaPasien.setText(intent.getStringExtra("namanya"));
            poli.setText(intent.getStringExtra("nm_poliklinikx"));
            nmDokter.setText(intent.getStringExtra("nm_dokterx"));
            hariTanggal.setText(tanggalHari);
            jamPraktek.setText(jammulaiselesai);
            asuransi.setText(intent.getStringExtra("asuransi"));
        }
    }

    private void initialiseRetrofitDaftar(){
//        Gson gson=new GsonBuilder()
//                .setLenient().create();
        if (intent.getStringExtra("asuransi").equalsIgnoreCase("BPJS")){
            retrofitDaftar=new Retrofit.Builder()
                    .baseUrl(Koneksi.URL_REG_PASIEN_BPJS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }else {
            retrofitDaftar = new Retrofit.Builder()
                    .baseUrl(Koneksi.URL_REG_PASIEN_UMUM)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    private void postMessageDaftarBPJS(View v){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("nomorkartu",intent.getStringExtra("nomorkartu"));
        jsonObject.addProperty("nik",intent.getStringExtra("nik"));
        jsonObject.addProperty("notelp",intent.getStringExtra("nohp"));
        jsonObject.addProperty("tanggalperiksa",intent.getStringExtra("tanggal"));
        jsonObject.addProperty("kodepoli",intent.getStringExtra("kdPoliklinik"));
        //jsonObject.addProperty("kodepoli","BED");
        jsonObject.addProperty("nomorreferensi",intent.getStringExtra("nomorreferensi"));
        jsonObject.addProperty("jenisreferensi",1);
        jsonObject.addProperty("jenisrequest",2);
        jsonObject.addProperty("polieksekutif",0);

        ApiService apiService=ApiHandler.createService(ApiService.class,Koneksi.URL_REG_PASIEN_BPJS);
        Call<JsonObject> call = apiService.postRawJSON(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    //Toast.makeText(RingkasanDaftarOnlineActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    Log.d("dataBpjs", "onResponse: "+response.body().toString());
                    JSONObject root=new JSONObject(response.body().toString());
                    JSONObject metadata=root.getJSONObject("metadata");

                    if (metadata.getString("message").equalsIgnoreCase("Ok")){
                        JSONObject reponse=root.getJSONObject("response");
                        Intent intent1=new Intent(RingkasanDaftarOnlineActivity.this,SummaryPendaftaranActivity.class);
                        intent1.putExtra("nomorantrean",reponse.getString("nomorantrean"));
                        intent1.putExtra("kodebooking",reponse.getString("kodebooking"));
                        intent1.putExtra("jenisantrean",reponse.getString("jenisantrean"));
                        intent1.putExtra("estimasidilayani",reponse.getString("estimasidilayani"));
                        intent1.putExtra("namapoli",reponse.getString("namapoli"));
                        intent1.putExtra("namadokter",reponse.getString("namadokter"));
                        intent1.putExtra("asuransi",intent.getStringExtra("asuransi"));
                        startActivity(intent1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RingkasanDaftarOnlineActivity.this, "Gagal Mendaftar, Pastikan Data Yang Anda Masukan Benar", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RingkasanDaftarOnlineActivity.this,MainActivity.class));
                finish();
                Log.d("DataBpjs", "onFailure: "+call.toString());
            }
        });


    }
    private void postMessageDaftar(){
        HashMap<String ,String > hashMap=new HashMap<>();
        hashMap.put("KD_REKAM_MEDIS",intent.getStringExtra("norm"));
        hashMap.put("KD_POLIKLINIK",intent.getStringExtra("kdPoliklinik"));
        hashMap.put("NIP_DOKTER",intent.getStringExtra("nip_dokterx"));
         hashMap.put("TANGGAL_PERIKSA",intent.getStringExtra("tanggal"));
        //hashMap.put("TANGGAL_PERIKSA","31/12/2020");
        RegUmumApiService apiService=retrofitDaftar.create(RegUmumApiService.class);
        Call<JsonObject> result=apiService.postMessageDaftar(hashMap);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //Toast.makeText(RingkasanDaftarOnlineActivity.this, "Berhasil Daftar", Toast.LENGTH_SHORT).show();
                Log.d("data", "onResponse: "+response.body().toString());
                try {
                    String nomorantrean="",kodebooking="",jamdilayani="",estimasidilayani="",namapoli="",namadokter="",hari="",tanggal="",jam_mulai="",jam_selesai = "";
                    JSONObject root = new JSONObject(response.body().toString());
                    JSONObject metadata=root.getJSONObject("metadata");
                    JSONObject responses=root.getJSONObject("response");
                    Intent intent1 = new Intent(RingkasanDaftarOnlineActivity.this, SummaryPendaftaranActivity.class);

                        nomorantrean=responses.getString("nomorantrean");
                        kodebooking=responses.getString("kodebooking");
                        jamdilayani=responses.getString("jamdilayani");
                        estimasidilayani=responses.getString("estimasidilayani");
                        namapoli=responses.getString("namapoli");
                        namadokter=responses.getString("namadokter");
                        hari=responses.getString("hari");
                        tanggal=responses.getString("tanggal");
                        jam_mulai=responses.getString("jam_mulai");
                        jam_selesai=responses.getString("jam_selesai");


                    if (metadata.getString("message").equalsIgnoreCase("Ok")){
                    intent1.putExtra("nomorantrean", nomorantrean);
                    intent1.putExtra("kodebooking", kodebooking);
                    intent1.putExtra("jamdilayani", jamdilayani);
                    intent1.putExtra("estimasidilayani", estimasidilayani);
                    intent1.putExtra("namapoli", namapoli);
                    intent1.putExtra("namadokter", namadokter);
                    intent1.putExtra("hari", hari);
                    intent1.putExtra("tanggal", tanggal);
                    intent1.putExtra("jam_mulai",jam_mulai);
                    intent1.putExtra("jam_selesai",jam_selesai);
                    intent1.putExtra("asuransi", intent.getStringExtra("asuransi"));
                    startActivity(intent1);}
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RingkasanDaftarOnlineActivity.this, "PASIEN TELAH TERDAFTAR UMUM KE DUA POLI. SILAKAN LAKUKAN PENDAFTARAN KE LOKET PENDAFTARAN UNTUK PENDFTARAN LAINNYA", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RingkasanDaftarOnlineActivity.this,MainActivity.class));
                Log.d("data", "onFailure: "+t.getMessage());
                Log.d("data", "onFailure: "+call.toString());
            }
        });
    }

}