package com.rsip.mobile.RecylcerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.Koneksi;
import com.rsip.mobile.View.DetailDokterActivity;
import com.rsip.mobile.View.ShareAllJadwalDokter;

import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;


public class JadwalDokterAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Intent intents;
    private TextView shareJadwal;


    private JadwalDokterAllAdapter mAdapter;

    private ArrayList<JadwalDokterAllModel> modelList = new ArrayList<>();
    ArrayList<JadwalDokterAllModel> singleItemList,selasaItemList,rabuItemList,kamisItemList,jumatItemList,sabtuItemList,allDataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_dokter_all);

        findViews();
        //setAdapter();
        getAllJadwal();


    }

    private void findViews() {
        shareJadwal=findViewById(R.id.tulisanBagikanJadwal);
        intents=getIntent();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        singleItemList=new ArrayList<>();
        selasaItemList=new ArrayList<>();
        rabuItemList=new ArrayList<>();
        kamisItemList=new ArrayList<>();
        jumatItemList=new ArrayList<>();
        sabtuItemList=new ArrayList<>();
        allDataList=new ArrayList<>();
        Log.d("findview", "findViews: "+intents.getStringExtra("poli"));
        Log.d("findview", "findViews: "+intents.getStringExtra("spinerPoli"));
        shareJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JadwalDokterAllActivity.this, ShareAllJadwalDokter.class);
                Log.d("allJadwal", "onClick: "+intents.getStringExtra("dokterall"));
                Log.d("allJadwal", "onClick: "+intents.getStringExtra("poli"));
                if (!TextUtils.isEmpty(intents.getStringExtra("dokterall"))) {
                    intent.putExtra("dari", intents.getStringExtra("dokterall"));
                }
                if (!TextUtils.isEmpty(intents.getStringExtra("poli"))){
                    intent.putExtra("dari", intents.getStringExtra("poli"));
                    intent.putExtra("darispiner",intents.getStringExtra("spinerPoli"));
                }

                startActivity(intent);
            }
        });
    }


    private void setAdapter() {
        modelList.add(new JadwalDokterAllModel("SENIN",singleItemList));
        modelList.add(new JadwalDokterAllModel("SELASA",selasaItemList));
        modelList.add(new JadwalDokterAllModel("RABU",rabuItemList));
        modelList.add(new JadwalDokterAllModel("KAMIS",kamisItemList));
        modelList.add(new JadwalDokterAllModel("JUMAT",jumatItemList));
        modelList.add(new JadwalDokterAllModel("SABTU",sabtuItemList));
        Log.d("newJadwal", "setAdapter: "+allDataList.size());
        mAdapter = new JadwalDokterAllAdapter(JadwalDokterAllActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new JadwalDokterAllAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, JadwalDokterAllModel model) {

                //handle item click events here
                //Toast.makeText(JadwalDokterAllActivity.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(JadwalDokterAllActivity.this, DetailDokterActivity.class);
                if (!TextUtils.isEmpty(intents.getStringExtra("dokterall"))) {
                    intent.putExtra("dari", intents.getStringExtra("dokterall"));
                }
                if (!TextUtils.isEmpty(intents.getStringExtra("poli"))){
                    intent.putExtra("dari", intents.getStringExtra("poli"));
                    intent.putExtra("darispiner",intents.getStringExtra("spinerPoli"));
                }
                intent.putExtra("kdPoliklinik",model.getKd_poliklinikx());
                intent.putExtra("nm_poliklinikx",model.getNm_poliklinikx());
                intent.putExtra("nip_dokterx",model.getNip_dokterx());
                intent.putExtra("nm_dokterx",model.getNm_dokterx());
                intent.putExtra("harix",model.getHarix());
                //intent.putExtra("tglx",model.getTglx());
                intent.putExtra("jam_mulaix",model.getJam_mulaix());
                intent.putExtra("jam_selesaix",model.getJam_selesaix());
                startActivity(intent);
                finish();

            }
        });


    }

    private void getAllJadwal(){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Koneksi.URL_TAMPIL_JADWAL_ALL_POLI, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response.length()>0){

                    try{
                        JSONArray root=response.getJSONArray("response");
                        for (int i = 0; i <root.length() ; i++) {
                            //ArrayList<JadwalDokterAllModel> singleItemList,selasaItemList,rabuItemList,kamisItemList,jumatItemList,sabtuItemList = new ArrayList<>();
                            JSONObject data=root.getJSONObject(i);
                            JadwalDokterAllModel jadwalDokterAllModel=new JadwalDokterAllModel();
                            jadwalDokterAllModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                            jadwalDokterAllModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                            jadwalDokterAllModel.setNip_dokterx(data.getString("nip_dokterx"));
                            jadwalDokterAllModel.setNm_dokterx(data.getString("nm_dokterx"));
                            jadwalDokterAllModel.setHarix(data.getString("harix"));
                            jadwalDokterAllModel.setJam_mulaix(data.getString("jam_mulaix"));
                            jadwalDokterAllModel.setJam_selesaix(data.getString("jam_selesaix"));

                            //dari intent dokter today
                            if (!TextUtils.isEmpty(intents.getStringExtra("dokterall"))) {
                                if (intents.getStringExtra("dokterall").equalsIgnoreCase("dokterall")) {
                                    if (data.getString("harix").equalsIgnoreCase("SENIN")) {
                                        singleItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("SELASA")) {
                                        selasaItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("RABU")) {
                                        rabuItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("KAMIS")) {
                                        kamisItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("JUMAT")) {
                                        jumatItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("SABTU")) {
                                        sabtuItemList.add(jadwalDokterAllModel);
                                    }

                                }
                            }
                            if (!TextUtils.isEmpty(intents.getStringExtra("poli"))) {
                                if (intents.getStringExtra("poli").equalsIgnoreCase("poli")) {
                                    if (data.getString("harix").equalsIgnoreCase("SENIN") && data.getString("nm_poliklinikx").equalsIgnoreCase(intents.getStringExtra("spinerPoli"))) {
                                        singleItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("SELASA") && data.getString("nm_poliklinikx").equalsIgnoreCase(intents.getStringExtra("spinerPoli"))) {
                                        selasaItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("RABU") && data.getString("nm_poliklinikx").equalsIgnoreCase(intents.getStringExtra("spinerPoli"))) {
                                        rabuItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("KAMIS") && data.getString("nm_poliklinikx").equalsIgnoreCase(intents.getStringExtra("spinerPoli"))) {
                                        kamisItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("JUMAT") && data.getString("nm_poliklinikx").equalsIgnoreCase(intents.getStringExtra("spinerPoli"))) {
                                        jumatItemList.add(jadwalDokterAllModel);
                                    }
                                    if (data.getString("harix").equalsIgnoreCase("SABTU") && data.getString("nm_poliklinikx").equalsIgnoreCase(intents.getStringExtra("spinerPoli"))) {
                                        sabtuItemList.add(jadwalDokterAllModel);
                                    }
                                }
                            }
                            allDataList.add(jadwalDokterAllModel);


                        }


                        setAdapter();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }


}
