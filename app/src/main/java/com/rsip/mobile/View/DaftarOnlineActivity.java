package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.PoliklinikActivity;
import com.rsip.mobile.Utils.JsonUtil;
import com.rsip.mobile.Utils.Koneksi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaftarOnlineActivity extends AppCompatActivity {

    Button btnLanjut;
    Spinner spinerTanggal,spinerRM,spinnerAsuransi;
    EditText noHP;
    RadioGroup listAsuransi;
    JsonUtil jsonUtil=new JsonUtil();
    List<String> isi=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_online);
        findViews();
    }

    private void findViews(){
        btnLanjut=findViewById(R.id.btn_lanjut);
        spinerTanggal=findViewById(R.id.spinner_Hari);
        spinerRM=findViewById(R.id.spinner_rm);
        listAsuransi=findViewById(R.id.radGroupAsuransi);
       // spinnerAsuransi=findViewById(R.id.spinner_Asuransi);
        noHP=findViewById(R.id.daftarOnlineNoHP);
        getHariAktif();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,isi);
//        dataAdapter.setDropDownViewResource
//                (android.R.layout.simple_spinner_dropdown_item);
        spinerTanggal.setAdapter(dataAdapter);
        jsonUtil.getHariAktif(this,spinerTanggal,isi);
       // selectAsuransi();
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=listAsuransi.getCheckedRadioButtonId();
                switch (id){
                    case R.id.radioBtnBpjs:

                            getAllDataForm("BPJS");

                        break;
                    case R.id.radioBtnUmum:

                            getAllDataForm("Umum");

                        break;
                }
            }
        });


    }

    private void selectAsuransi(){

//        listAsuransi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.radioBtnBpjs:
//                        btnLanjut.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                getAllDataForm("BPJS");
//                            }
//                        });
//                        break;
//                    case R.id.radioBtnUmum:
//                        btnLanjut.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //startActivity(new Intent(DaftarOnlineActivity.this, PoliklinikActivity.class));
//                                getAllDataForm("Umum");
//
//                            }
//                        });
//                        break;
//                }
//            }
//        });
    }

    private void radioChecked(View view){
        Boolean check=((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.radioBtnBpjs:
                if (check) {
                    getAllDataForm("BPJS");
                }
                break;
            case R.id.radioBtnUmum:
                if (check) {
                    getAllDataForm("Umum");
                }
                break;
        }
    }

    private void getAllDataForm(String asuransi){
        Intent intent=new Intent(DaftarOnlineActivity.this,PoliklinikActivity.class);
        //membelah kata
        String s=spinerTanggal.getSelectedItem().toString();
        String s1=s.substring(s.indexOf(" "));

        String erem=spinerRM.getSelectedItem().toString();
        String rm=erem.substring(0,erem.indexOf(" "));

        String hari=spinerTanggal.getSelectedItem().toString();
        String harinya=hari.substring(0,hari.indexOf(" "));

        String namaRm=spinerRM.getSelectedItem().toString();

        String namanya=namaRm.substring(namaRm.indexOf(" "));

        String outputDate="";

        DateFormat inputFormat=new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date=inputFormat.parse(s1);
            outputDate=outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(DaftarOnlineActivity.this, outputDate, Toast.LENGTH_SHORT).show();

        intent.putExtra("norm",rm);
        intent.putExtra("namanya",namanya);
        intent.putExtra("asuransi",asuransi);
        intent.putExtra("nohp",noHP.getText().toString());
       // intent.putExtra("tanggal",outputDate);
        intent.putExtra("tanggal","31/12/2020");
        intent.putExtra("hari",harinya);
        Log.d("asuransi", "getAllDataForm: "+asuransi);
        startActivity(intent);
    }

    public void getHariAktif(){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Koneksi.URL_TAMPIL_HARI_AKTIF, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray respone=response.getJSONArray("response");
                    for (int i = 0; i <respone.length() ; i++) {
                        JSONObject data=respone.getJSONObject(i);
                        String tampung=data.getString("hari");
                        tampung+="   "+data.getString("tanggal");
                        isi.add(tampung);
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
        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }
}