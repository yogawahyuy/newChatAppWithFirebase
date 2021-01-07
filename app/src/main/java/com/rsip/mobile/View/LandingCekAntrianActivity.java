package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.Koneksi;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingCekAntrianActivity extends AppCompatActivity {
    private Retrofit retrofit;
    EditText editTextTanggalAntrian;
    SearchableSpinner spinnerPoliklinik,spinnerDokter;
    private ArrayList<SemuaDokterModel> modelList = new ArrayList<>();
    private ArrayList<String> isiPoli=new ArrayList<>();
    private ArrayList<String> isi=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_cek_antrian);
        findViews();
    }

    private void findViews(){
        initialRetrofit();
        spinnerPoliklinik=findViewById(R.id.spiner_antrian_poliklinik);
        spinnerDokter=findViewById(R.id.spiner_dokter_antrian);
        editTextTanggalAntrian=findViewById(R.id.edittxt_tanggalantrian);
        editTextTanggalAntrian.setOnClickListener(v ->  {
            showDatePicker();
        });
        selectPoli();
        ArrayAdapter<SemuaDokterModel> dataAdapter=new ArrayAdapter<SemuaDokterModel>(this,android.R.layout.simple_spinner_item,modelList);

    }
    private void showDatePicker(){
        final Calendar cldr=Calendar.getInstance();
        int day=cldr.get(Calendar.DAY_OF_MONTH);
        int month=cldr.get(Calendar.MONTH);
        int year=cldr.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            editTextTanggalAntrian.setText(dayOfMonth+"/"+(month1 +1)+"/"+ year1);
            String tanggal=dayOfMonth+"/"+(month1 +1)+"/"+ year1;
            getNamaPoli(tanggal);

            ArrayAdapter<String > spinnerAdapterPoli=new ArrayAdapter<String>(LandingCekAntrianActivity.this,android.R.layout.simple_spinner_item,isiPoli);
            spinnerPoliklinik.setSelection(3);
            spinnerPoliklinik.setAdapter(spinnerAdapterPoli);

        },year,month,day);
        datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis()-1000)-(1000*60*60*24*7));
        datePickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis()-1000)+(1000*60*60*24*7));
        datePickerDialog.show();
    }

    private void selectPoli(){
        spinnerPoliklinik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (TextUtils.isEmpty(editTextTanggalAntrian.getText().toString())){
                    Toast.makeText(LandingCekAntrianActivity.this, "Tanggal Harus diisi!", Toast.LENGTH_SHORT).show();
                }else{
                    String text=editTextTanggalAntrian.getText().toString();
                    String hasilSpiner=spinnerPoliklinik.getSelectedItem().toString();
                    Log.d("hasilSpiner", "onItemSelected: "+hasilSpiner);
                    Log.d("hasilTanggal", "onItemSelected: "+text);
//                    if (isi.size()>0) {
//                        isi.clear();
//                    }
                    getNamaDokter(text,hasilSpiner);
                    ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(LandingCekAntrianActivity.this,android.R.layout.simple_spinner_item,isi);
                    spinnerDokter.setAdapter(spinnerAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getNamaDokter(String textTanggal, String spinner ){
        HashMap<String , String > param=new HashMap<>();
        param.put("TANGGAL_PERIKSA",textTanggal);
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
//                        semuaDokterModel.setKd_poliklinikx(data.getString("kd_poli_bpjsx"));
//                        semuaDokterModel.setNm_poliklinikx(data.getString("nm_poli_bpjsx"));
//                        semuaDokterModel.setNip_dokterx(data.getString("nip_dokterx"));
//                        semuaDokterModel.setNm_dokterx(data.getString("nm_dokterx"));
//                        semuaDokterModel.setHarix(data.getString("harix"));
//                        semuaDokterModel.setTglx(data.getString("tglx"));
//                        semuaDokterModel.setJam_mulaix(data.getString("jam_mulaix"));
//                        semuaDokterModel.setJam_selesaix(data.getString("jam_selesaix"));

                        if (data.getString("nm_poliklinikx").equalsIgnoreCase(spinner)) {
                            modelList.add(semuaDokterModel);
                            String tampung = modelList.get(i).getNm_dokterx();

                            isi.add(tampung);
                        }
                        Log.d("ukuran modelis", "onResponse: "+modelList.size());

                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void getNamaPoli(String textTanggal ){
        HashMap<String , String > param=new HashMap<>();
        param.put("TANGGAL_PERIKSA",textTanggal);
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
//                        semuaDokterModel.setKd_poliklinikx(data.getString("kd_poli_bpjsx"));
//                        semuaDokterModel.setNm_poliklinikx(data.getString("nm_poli_bpjsx"));
//                        semuaDokterModel.setNip_dokterx(data.getString("nip_dokterx"));
//                        semuaDokterModel.setNm_dokterx(data.getString("nm_dokterx"));
//                        semuaDokterModel.setHarix(data.getString("harix"));
//                        semuaDokterModel.setTglx(data.getString("tglx"));
//                        semuaDokterModel.setJam_mulaix(data.getString("jam_mulaix"));
//                        semuaDokterModel.setJam_selesaix(data.getString("jam_selesaix"));
                        modelList.add(semuaDokterModel);
                        String tampung = modelList.get(i).getNm_poliklinikx();
                        isiPoli.add(tampung);

                        Log.d("ukuran modelis", "onResponse: "+modelList.size());

                    }
                    Set<String> set=new HashSet<>(isiPoli);
                    isiPoli.clear();
                    isiPoli.addAll(set);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void initialRetrofit(){
        retrofit=new Retrofit.Builder()
                .baseUrl(Koneksi.URL_TAMPIL_JADWAL_POLI_UMUM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


//    private void getDataToSpinner(){
//        HashMap<String , String > hashMap=new HashMap<>();
//        hashMap.put("TANGGAL_PERIKSA",)
//    }
}