package com.example.chatwithfirebase.Utils;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatwithfirebase.Model.InfoBedModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public ArrayList<InfoBedModel> infoBedModels = new ArrayList<>();

    public JsonUtil() {
    }

    public void getInfoBed(Context context, final RecyclerView.Adapter adapter, final List<InfoBedModel> infobed){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, "http://103.81.195.2:5758/bpjs/bpjsApi/kamar/getKamarSedia", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("tampil");
                        Log.e("json utils", "jumlah array: "+jsonArray.length() );
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            InfoBedModel infoBedModel = new InfoBedModel();
                            infoBedModel.setNamaRuang(data.getString("nm_ruang"));
                            infoBedModel.setKodeRuang(data.getString("kd_ruang"));
                            infoBedModel.setKodeKelas(data.getString("kd_kelas"));
                            infoBedModel.setKapasitas(data.getString("kapasitas"));
                            infoBedModel.setTersedia(data.getString("tersedia"));
                            infobed.add(infoBedModel);
                        }
                        Log.e("JsonUtils", "onResponse: gagal atau berhasil");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
