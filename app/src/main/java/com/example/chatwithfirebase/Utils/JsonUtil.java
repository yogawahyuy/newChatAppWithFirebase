package com.example.chatwithfirebase.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatwithfirebase.Model.AyatModel;
import com.example.chatwithfirebase.Model.InfoBedModel;
import com.example.chatwithfirebase.Model.MenuSurahModel;
import com.google.android.gms.dynamic.IFragmentWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {


    public JsonUtil() {
    }

    public void getInfoBed(Context context, final RecyclerView.Adapter adapter, final List<InfoBedModel> infobed, final ProgressDialog progressDialog){
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
                        progressDialog.dismiss();
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
                Log.i("JsonUtils", "onErrorResponse: "+error);
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
    public void getSurah(Context context, final RecyclerView.Adapter adapter, final List<MenuSurahModel> surah, final ProgressDialog progressDialog){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://raw.githubusercontent.com/penggguna/QuranJSON/master/quran.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length()>0){
                    try {
                        Log.e("json utils", "jumlah array: "+response.length() );
                        for (int i = 0; i <response.length() ; i++) {
                            JSONObject data=response.getJSONObject(i);
                            MenuSurahModel surahModel=new MenuSurahModel();
                            surahModel.setNameSurah(data.getString("name"));
                            surahModel.setNumberAyah(String.valueOf(data.getInt("number_of_ayah"))+" Ayat");
                            surahModel.setNumberSurah(String.valueOf(data.getInt("number_of_surah")));
                            surahModel.setTypeSurah(data.getString("type"));
                            JSONObject transaltion=data.getJSONObject("name_translations");
                            surahModel.setArTranslation(transaltion.getString("ar"));
                            surahModel.setIdTranslation(transaltion.getString("id"));
                            surah.add(surahModel);
                        }
                        progressDialog.dismiss();
                    }catch (JSONException e){
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
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void getAyat(Context context, final RecyclerView.Adapter adapter, final List<AyatModel> surah, final ProgressDialog progressDialog, final int idSurah, final TextView title, final TextView type, final TextView translations, final TextView jumlah){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://raw.githubusercontent.com/penggguna/QuranJSON/master/surah/" + idSurah + ".json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length()>0){
                    try{
                        Log.e("json utils", "jumlah array: "+response.length() );
                        AyatModel ayatModel=new AyatModel();
                        String nameSurah=response.getString("name");
                        String typeSurah=response.getString("type");
                        JSONObject translation=response.getJSONObject("name_translations");
                        String idTranslation=translation.getString("id");
                        int jumlahAyat=response.getInt("number_of_ayah");
                        ayatModel.setNamaSurah(nameSurah);
                        ayatModel.setTypeSurah(typeSurah);
                        ayatModel.setIdTranslation(idTranslation);
                        ayatModel.setJumlahAyat(String.valueOf(jumlahAyat));
                        title.setText(nameSurah);
                        translations.setText(idTranslation);
                        type.setText(typeSurah);
                        jumlah.setText(jumlahAyat+" Ayat");
                        JSONArray verse=response.getJSONArray("verses");
//                        for (int i = 0; i <verse.length() ; i++) {
//                            JSONObject data=verse.getJSONObject(i);
//                            AyatModel ayatModel1=new AyatModel();
//                            ayatModel1.setNumberAyat(String.valueOf(data.getInt("number")));
////                            ayatModel1.setTextArab(data.getString("text"));
////                            ayatModel1.setIdTranslationAyat(data.getString("translation_id"));
//                            surah.add(ayatModel1);
//                        }
                        progressDialog.dismiss();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JsonUtils", "onErrorResponse: "+error);
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getAyat(Context context, final RecyclerView.Adapter adapter, final List<AyatModel> surah, final int idSurah){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://raw.githubusercontent.com/rioastamal/quran-json/master/surah/" + idSurah + ".json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("json utils", "jumlah array: "+response.length() );
                try {
                    JSONObject data=response.getJSONObject(String.valueOf(idSurah));
                    int numberAyah=Integer.valueOf(data.getString("number_of_ayah"));
                    for (int i = 0; i <numberAyah ; i++) {
                        JSONObject textArab=data.getJSONObject("text");
                        AyatModel ayatModel1=new AyatModel();
                        ayatModel1.setNumberAyat(String.valueOf(i+1));
                        ayatModel1.setTextArab(textArab.getString(String.valueOf(i+1)));
                        JSONObject translation=data.getJSONObject("translations");
                        JSONObject id=translation.getJSONObject("id");
                        JSONObject idTranslation=id.getJSONObject("text");
                        ayatModel1.setIdTranslationAyat(idTranslation.getString(String.valueOf(i+1)));
                        surah.add(ayatModel1);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JsonUtils", "onErrorResponse: "+error);
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}
