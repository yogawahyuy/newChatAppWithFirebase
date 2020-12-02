package com.rsip.mobile.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rsip.mobile.Model.AyatModel;
import com.rsip.mobile.Model.InfoBedModel;
import com.rsip.mobile.Model.JadwalSolatModel;
import com.rsip.mobile.Model.KalendarHijriModel;
import com.rsip.mobile.Model.MenuSurahModel;
import com.rsip.mobile.Model.QuoteModel;
import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.PoliklinikModel;
import com.rsip.mobile.View.KalendarHijriActivity;
import com.rsip.mobile.View.SemuaDokterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonUtil {


    public JsonUtil() {
    }

    public void getInfoBed(Context context, final RecyclerView.Adapter adapter, final List<InfoBedModel> infobed, final ProgressDialog progressDialog){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, "http://103.255.241.124:5758/bpjs/bpjsApi/kamar/getKamarSedia", null, new Response.Listener<JSONObject>() {
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
                        Log.d("jsonUUtil", "onResponse: "+e.getMessage());
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

    public void getJadwalAllPoli(Context context, final RecyclerView.Adapter adapter, final List<SemuaDokterModel> semuaDokterModels,String hariSortir){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Koneksi.URL_TAMPIL_JADWAL_ALL_POLI, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length()>0){
                    Log.d("getJadwal", "onResponse: "+response.length());
                    try{
                        JSONArray root=response.getJSONArray("response");
                        for (int i = 0; i <root.length() ; i++) {
                            JSONObject data=root.getJSONObject(i);
                            SemuaDokterModel semuaDokterModel=new SemuaDokterModel();
                            semuaDokterModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                            semuaDokterModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                            semuaDokterModel.setNip_dokterx(data.getString("nip_dokterx"));
                            semuaDokterModel.setNm_dokterx(data.getString("nm_dokterx"));
                            semuaDokterModel.setHarix(data.getString("harix"));
                            //semuaDokterModel.setTglx(data.getString("tglx"));
                            semuaDokterModel.setJam_mulaix(data.getString("jam_mulaix"));
                            semuaDokterModel.setJam_selesaix(data.getString("jam_selesaix"));
                            if (data.getString("harix").equalsIgnoreCase(hariSortir)){
                                semuaDokterModels.add(semuaDokterModel);
                            }else{
                                semuaDokterModels.add(semuaDokterModel);
                            }

                        }
                    }catch (Exception e){
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
                            surahModel.setRectiation(data.getString("recitation"));
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

                        JSONArray rectitation= response.getJSONArray("recitations");
                        for (int i = 0; i <rectitation.length() ; i++) {
                            JSONObject data=rectitation.getJSONObject(i);
                            ayatModel.setRectiation(data.getString("audio_url"));
                            Log.d("rectitation", "onResponse: "+ayatModel.getRectiation());
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

    public void getJadwalSholat(Context context, final List<JadwalSolatModel> jadwals, String tanggal, String tempat, final TextView subuh, final TextView duhur, final TextView ashar, final TextView magrib, final TextView isya,final ProgressDialog progressDialog){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://api.aladhan.com/v1/timingsByAddress?address="+tempat+"/"+tanggal+"&method=11", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONObject jadwal=data.getJSONObject("timings");
                    JadwalSolatModel jadwalSolatModel=new JadwalSolatModel();
                    jadwalSolatModel.setAzhar(jadwal.getString("Asr"));
                    jadwalSolatModel.setDuha(jadwal.getString("Sunrise"));
                    jadwalSolatModel.setDuhur(jadwal.getString("Dhuhr"));
                    jadwalSolatModel.setImsak(jadwal.getString("Imsak"));
                    jadwalSolatModel.setIsya(jadwal.getString("Isha"));
                    jadwalSolatModel.setMagrib(jadwal.getString("Maghrib"));
                    jadwalSolatModel.setSubuh(jadwal.getString("Fajr"));
                    //jadwalSolatModel.setTanggal(jadwal.getString("tanggal"));
                    Log.d("jadwalsolat", "onResponse: "+jadwal.getString("Fajr"));
                    subuh.setText(jadwalSolatModel.getSubuh());
                    duhur.setText(jadwalSolatModel.getDuhur());
                    ashar.setText(jadwalSolatModel.getAzhar());
                    magrib.setText(jadwalSolatModel.getMagrib());
                    isya.setText(jadwalSolatModel.getIsya());
                    jadwals.add(jadwalSolatModel);
                    progressDialog.dismiss();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getKabupaten(final Context context, final List<String> kab, final Spinner spinner, final ProgressDialog progressDialog){
        JsonArrayRequest  jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://raw.githubusercontent.com/ibnux/data-indonesia/master/kabupaten/33.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for (int i = 0; i <response.length() ; i++) {
                        JSONObject data=response.getJSONObject(i);
                        kab.add(data.getString("nama"));
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,kab);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(1);
                    progressDialog.dismiss();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void getDateHijri(final Context context, final List<KalendarHijriModel> kalendarHijri,String tanggal,TextView days,TextView month,TextView years,TextView dates,TextView holidays){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://api.aladhan.com/v1/gToH?date=" + tanggal, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {
                    try {
                        Log.d("jsonDateHijri", "onResponse: " + response.length());
                        KalendarHijriModel kalendarHijriModel=new KalendarHijriModel();

                        JSONObject data = response.getJSONObject("data");
                        JSONObject hijri=data.getJSONObject("hijri");
                        String date=hijri.getString("date");
                        String day=hijri.getString("day");
                        JSONObject monthObject=hijri.getJSONObject("month");
                        String nameMonth=monthObject.getString("en");
                        String year=hijri.getString("year");

                        kalendarHijriModel.setDateHijriah(date);
                        kalendarHijriModel.setDayHijriah(day);
                        kalendarHijriModel.setMonthHijriah(nameMonth);
                        kalendarHijriModel.setYearHijriah(year);
                        days.setText(day);
                        month.setText(nameMonth);
                        years.setText(year);
                        dates.setText(date);

                        JSONArray holiday=hijri.getJSONArray("holidays");
                        Log.d("holiyay", "onResponse: "+holiday.length());
                        if (holiday.length()==0){
                            kalendarHijriModel.setHolidays("Tidak Ada Libur");
                            holidays.setText("Tidak Ada Acara");
                            Log.d("holidays", "onResponse: "+kalendarHijriModel.getHolidays());
                        }else{
                            for (int i = 0; i <holiday.length() ; i++) {
                                kalendarHijriModel.setHolidays(holiday.getString(i));
                                holidays.setText(holiday.getString(0));
                            }
                        }
                        //progressDialog.dismiss();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getQuotes(Context context,TextView quoteText,TextView quoteAuthor){
        Random random=new Random();
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://raw.githubusercontent.com/JamesFT/Database-Quotes-JSON/master/quotes.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<QuoteModel> quoteModelList=new ArrayList<>();

                    String textQuote,authorQuote;
                    Log.d("hasilquote", "onResponse: "+response.length());
                    for (int i = 0; i <response.length() ; i++) {
                        QuoteModel quoteModel=new QuoteModel();
                        JSONObject data=response.getJSONObject(i);
                        quoteModel.setQuoteText(data.getString("quoteText"));
                        quoteModel.setQuoteAuthor(data.getString("quoteAuthor"));
                        textQuote=data.getString("quoteText");
                        quoteModelList.add(quoteModel);
                    }

                    int hasilRandom=random.nextInt(response.length());
                    quoteText.setText(quoteModelList.get(hasilRandom).getQuoteText());
                    quoteAuthor.setText(quoteModelList.get(hasilRandom).getQuoteAuthor());

                    Log.d("hasilrandom", "onResponse: "+hasilRandom);
                    Log.d("hasilrandom", "onResponse: "+quoteModelList.size());
                    Log.d("hasilrandom", "onResponse: "+quoteModelList.get(1).getQuoteText());
                    Log.d("hasilrandom", "onResponse: "+quoteModelList.get(1).getQuoteAuthor());
                }catch (JSONException e){
                    e.getMessage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void getQuotesIslamic(Context context,TextView quoteText,TextView quouteAuthor,TextView surahNo,TextView ayahNo,ProgressDialog progressDialog){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://unpkg.com/quran-json@1.0.1/json/quran/id.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<QuoteModel> quoteModelList=new ArrayList<>();
                try {
                    for (int i = 0; i <response.length() ; i++) {
                        QuoteModel quoteModel=new QuoteModel();
                        JSONObject data=response.getJSONObject(i);
                        quoteModel.setQuoteText(data.getString("text"));
                        quoteModel.setQuoteAuthor(data.getString("translation"));
                        quoteModel.setSurahNo(data.getString("surah_number"));
                        quoteModel.setAyahNo(data.getString("verse_number"));
                        quoteModelList.add(quoteModel);

                    }
                    Random random=new Random();
                    int hasilRandom=random.nextInt(response.length());
                    quoteText.setText(quoteModelList.get(hasilRandom).getQuoteText());
                    quouteAuthor.setText(quoteModelList.get(hasilRandom).getQuoteAuthor());
                    surahNo.setText(quoteModelList.get(hasilRandom).getSurahNo());
                    ayahNo.setText(quoteModelList.get(hasilRandom).getAyahNo());
                    progressDialog.dismiss();
                }catch (JSONException e){
                    e.getMessage();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void getPoliklinik(Context context,String url, String tglPeriksa, final RecyclerView.Adapter adapter, final List<PoliklinikModel> poliklinikModels){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    JSONArray list = root.getJSONArray("list");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject data= list.getJSONObject(i);
                        PoliklinikModel poliklinikModel=new PoliklinikModel();
                        poliklinikModel.setKd_poliklinikx(data.getString("kd_poliklinikx"));
                        poliklinikModel.setNm_poliklinikx(data.getString("nm_poliklinikx"));
                        poliklinikModel.setNip_dokterx(data.getString("nip_dokterx"));
                        poliklinikModel.setNm_dokterx(data.getString("nm_dokterx"));
                        poliklinikModel.setHarix(data.getString("harix"));
                        poliklinikModel.setTglx(data.getString("tglx"));
                        poliklinikModel.setJam_mulaix(data.getString("jam_mulaix"));
                        poliklinikModel.setJam_selesaix(data.getString("jam_selesaix"));
                        poliklinikModels.add(poliklinikModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    public void getHariAktif(Context context,Spinner spinner,List spinnerList){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Koneksi.URL_TAMPIL_HARI_AKTIF, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray respone=response.getJSONArray("respone");
                    for (int i = 0; i <respone.length() ; i++) {
                       JSONObject data=respone.getJSONObject(i);
                       String tampung=data.getString("hari");
                       tampung+=data.getString("tanggal");
                       spinnerList.add(tampung);
                        Log.d("data", "onResponse: "+tampung);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
