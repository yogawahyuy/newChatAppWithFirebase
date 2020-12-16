package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.rsip.mobile.BuildConfig;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.DokterTodayModel;
import com.rsip.mobile.RecylcerView.JadwalDokterAllModel;
import com.rsip.mobile.Utils.Koneksi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShareAllJadwalDokter extends AppCompatActivity {
    private Retrofit retrofit;
    private ArrayList<DokterTodayModel> modelList = new ArrayList<>();
    private String curentDate,saveDate;
    private TableLayout tableLayout;
    private TextView judul;
    private RelativeLayout relativeLayout;
    ConstraintLayout constraintLayout;
    ScrollView scrollView;
    Button btnShare;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_all_jadwal_dokter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findViews();
    }

    private void findViews(){
        intent=getIntent();
        tableLayout = (TableLayout)this.findViewById(R.id.tableLayout);
        judul=findViewById(R.id.textViewJudulJadwal);
        relativeLayout=findViewById(R.id.rel_picture);
        constraintLayout=findViewById(R.id.constraintJadwal);
        scrollView=findViewById(R.id.scrollContainer);
        btnShare=findViewById(R.id.btnShare);
        retrofit = new Retrofit.Builder()
                .baseUrl(Koneksi.URL_TAMPIL_JADWAL_POLI_UMUM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        if(!TextUtils.isEmpty(intent.getStringExtra("dokterToday"))) {
            getJadwal();
        }else if(!TextUtils.isEmpty(intent.getStringExtra("alldokter"))){
            getJadwalDokter();
        } else{
            getAllJadwal();
        }
        initTable();
        Log.d("intentss", "onResponse: "+intent.getStringExtra("dari"));
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                btnShare.performClick();
//            }
//        },5000);

    }

    private void getCurrentDate(){
        Date c= Calendar.getInstance().getTime();
        Log.d("tanggal", "getCurentDate: "+c);
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        curentDate=dateFormat.format(c);
        SimpleDateFormat dateFormat1=new SimpleDateFormat("MM-dd-yyyy");
        saveDate=dateFormat1.format(c);
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
        judul.setText("Jadwal Dokter Hari "+hasilHaris + " \n Tanggal "+curentDate);
        Log.d("hasilhari", "getJadwal: "+hasilHaris);
        HashMap<String,String> param=new HashMap<>();
        //param.put("TANGGAL_PERIKSA",tanggal);
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

                        if (data.getString("harix").equalsIgnoreCase(hasilHaris)) {
                            modelList.add(todayModel);
                            Log.d("tanggal", "onResponse: "+modelList.get(i).getNm_dokterx());
                        }
                    }
                    initTable();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
    private void getJadwalDokter(){
        HashMap<String,String> param=new HashMap<>();
        //param.put("TANGGAL_PERIKSA",tanggal);
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
                        DokterTodayModel todayModel=new DokterTodayModel();
                        todayModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                        todayModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                        todayModel.setNip_dokterx(data.getString("nip_dokterx"));
                        todayModel.setNm_dokterx(data.getString("nm_dokterx"));
                        todayModel.setHarix(data.getString("harix"));
                        todayModel.setTglx(data.getString("tglx"));
                        todayModel.setJam_mulaix(data.getString("jam_mulaix"));
                        todayModel.setJam_selesaix(data.getString("jam_selesaix"));

//                        if (data.getString("harix").equalsIgnoreCase(hasilHaris)) {
//                            modelList.add(todayModel);
//                            Log.d("tanggal", "onResponse: "+modelList.get(i).getNm_dokterx());
//                        }
                        modelList.add(todayModel);
                    }
                    initTable();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
    private void initTable(){
        TableLayout tl=findViewById(R.id.tableLayout);

        for (int i = 0; i <modelList.size() ; i++) {
            TableRow row=(TableRow)getLayoutInflater().inflate(R.layout.layout_row,null);
            String nomer=String.valueOf(i+1);
            String jamPraktek=modelList.get(i).getJam_mulaix();
            jamPraktek+=" - "+modelList.get(i).getJam_selesaix();
            ((TextView) row.findViewById(R.id.idnotabel)).setText(nomer);
            ((TextView) row.findViewById(R.id.idDoktertabel)).setText(modelList.get(i).getNm_dokterx());
            ((TextView) row.findViewById(R.id.idPolitabel)).setText(modelList.get(i).getNm_poliklinikx());
            ((TextView) row.findViewById(R.id.idHaritabel)).setText(modelList.get(i).getHarix());
            ((TextView) row.findViewById(R.id.idJamtabel)).setText(jamPraktek);
            tl.addView(row);
        }
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
//                            JadwalDokterAllModel jadwalDokterAllModel=new JadwalDokterAllModel();
//                            jadwalDokterAllModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
//                            jadwalDokterAllModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
//                            jadwalDokterAllModel.setNip_dokterx(data.getString("nip_dokterx"));
//                            jadwalDokterAllModel.setNm_dokterx(data.getString("nm_dokterx"));
//                            jadwalDokterAllModel.setHarix(data.getString("harix"));
//                            jadwalDokterAllModel.setJam_mulaix(data.getString("jam_mulaix"));
//                            jadwalDokterAllModel.setJam_selesaix(data.getString("jam_selesaix"));

                            DokterTodayModel todayModel=new DokterTodayModel();
                            todayModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                            todayModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                            todayModel.setNip_dokterx(data.getString("nip_dokterx"));
                            todayModel.setNm_dokterx(data.getString("nm_dokterx"));
                            todayModel.setHarix(data.getString("harix"));
                            //todayModel.setTglx(data.getString("tglx"));
                            todayModel.setJam_mulaix(data.getString("jam_mulaix"));
                            todayModel.setJam_selesaix(data.getString("jam_selesaix"));

                            //dari intent dokterall
                            if (!TextUtils.isEmpty(intent.getStringExtra("dari"))) {
                                judul.setText("Jadwal Semua Dokter");
                                if (intent.getStringExtra("dari").equalsIgnoreCase("dokterall")) {
                                    modelList.add(todayModel);
                                }

                            }

                            if (!TextUtils.isEmpty(intent.getStringExtra("dari"))) {
                                judul.setText("Jadwal Dokter Poli "+intent.getStringExtra("darispiner"));
                                if (intent.getStringExtra("dari").equalsIgnoreCase("poli")) {
                                    if (data.getString("nm_poliklinikx").equalsIgnoreCase(intent.getStringExtra("darispiner")))
                                    modelList.add(todayModel);
                                }
                            }
                        }
                        initTable();
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

    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapFromView(View view,int width,int height){
        Log.d("databitmap", "getBitmapFromView: "+view.getWidth()+" "+view.getHeight());
        Bitmap returnBitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bitmap returnBitmap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(returnBitmap);
//        Drawable bgDrawable=view.getBackground();
//        if (bgDrawable!=null)bgDrawable.draw(canvas);
//        else canvas.drawColor(android.R.color.white);
        view.draw(canvas);
        return returnBitmap;
    }
    private void shareImage(){
//        int totalWidth=scrollView.getChildAt(0).getWidth();
//        int totalHeight=scrollView.getChildAt(0).getHeight();
        int totalWidth=relativeLayout.getChildAt(0).getWidth();
        int totalHeight=relativeLayout.getChildAt(0).getHeight();
        Bitmap bitmap = getBitmapFromView(relativeLayout,totalWidth,totalHeight);
        //String fileName="RsiMobile-"+surahNo.getText().toString()+".jpg";
        String filePath=this.getFilesDir().getPath().toString() + "/RsiMobile-"+saveDate+".PNG";
        File file=new File(filePath);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            file.setReadable(true,false);
            Uri uri= FileProvider.getUriForFile(ShareAllJadwalDokter.this, BuildConfig.APPLICATION_ID+".provider",file);
            Intent intentShare=new Intent(Intent.ACTION_SEND);
            intentShare.putExtra(Intent.EXTRA_STREAM,uri);
            intentShare.setType("image/jpg");
            startActivity(Intent.createChooser(intentShare,"Bagikan Ke"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}