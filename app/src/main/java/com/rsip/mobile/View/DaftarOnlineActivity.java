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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
    Spinner spinerTanggal,spinnerAsuransi;
    EditText noHP;
    RadioGroup listAsuransi;
    EditText noKartuBpjs,nik,noRujukan,noRM;
    LinearLayout linNik,linNoRujukan;
    TextView titleBpjs;
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
        listAsuransi=findViewById(R.id.radGroupAsuransi);
       // spinnerAsuransi=findViewById(R.id.spinner_Asuransi);

        noRM=findViewById(R.id.edttext_norm);
        noHP=findViewById(R.id.daftarOnlineNoHP);
        noKartuBpjs=findViewById(R.id.edttext_noKartuBpjs);
        noKartuBpjs.setVisibility(View.GONE);
        nik=findViewById(R.id.edttext_nik);
        noRujukan=findViewById(R.id.edttext_noRujukan);
        titleBpjs=findViewById(R.id.titelBpjs);
        linNik=findViewById(R.id.lin_nik);
        linNoRujukan=findViewById(R.id.lin_noRujukan);
        linNik.setVisibility(View.GONE);
        linNoRujukan.setVisibility(View.GONE);
        getHariAktif();
        jsonUtil.getHariAktif(this,spinerTanggal,isi);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,isi);
//        dataAdapter.setDropDownViewResource
//                (android.R.layout.simple_spinner_dropdown_item);
        spinerTanggal.setSelection(1);
        spinerTanggal.setAdapter(dataAdapter);
        listAsuransi.setSelected(true);

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
        selectAsuransi();

    }

    private void selectAsuransi(){

        listAsuransi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioBtnBpjs:
                        noKartuBpjs.setVisibility(View.VISIBLE);
                        linNik.setVisibility(View.VISIBLE);
                        linNoRujukan.setVisibility(View.VISIBLE);
                        noRM.setVisibility(View.GONE);
                        titleBpjs.setText("No Kartu BPJS");
                        break;
                    case R.id.radioBtnUmum:
                        noKartuBpjs.setVisibility(View.GONE);
                        linNoRujukan.setVisibility(View.GONE);
                        linNik.setVisibility(View.GONE);
                        noRM.setVisibility(View.VISIBLE);
                        titleBpjs.setText("Nomor CM / Rekam Medik");
                        break;
                }
            }
        });
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

//        String erem=spinerRM.getSelectedItem().toString();
//        String rm=erem.substring(0,erem.indexOf(" "));

        String hari=spinerTanggal.getSelectedItem().toString();
        String harinya=hari.substring(0,hari.indexOf(" "));

        //String namaRm=spinerRM.getSelectedItem().toString();

      //  String namanya=namaRm.substring(namaRm.indexOf(" "));

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
        if (asuransi.equalsIgnoreCase("BPJS")){
            intent.putExtra("nomorkartu",noKartuBpjs.getText().toString());
            intent.putExtra("nik",nik.getText().toString());
            intent.putExtra("asuransi",asuransi);
            intent.putExtra("nomorreferensi",noRujukan.getText().toString());
            intent.putExtra("nohp",noHP.getText().toString());
            intent.putExtra("tanggal",outputDate);
            //intent.putExtra("tanggal", "30/12/2020");
            intent.putExtra("jenisreferensi",1);
            intent.putExtra("jenisrequest",2);
            intent.putExtra("polieksekutif",0);
            Log.d("datadaftar", "getAllDataForm: "+outputDate);
            startActivity(intent);
        }else {
//        intent.putExtra("norm",rm);
//        intent.putExtra("namanya",namanya);
            intent.putExtra("norm", noRM.getText().toString());
            intent.putExtra("namanya", "Default");
            intent.putExtra("asuransi", asuransi);
            intent.putExtra("nohp", noHP.getText().toString());
            intent.putExtra("tanggal",outputDate);
           // intent.putExtra("tanggal", "30/12/2020");
            intent.putExtra("hari", harinya);
            Log.d("asuransi", "getAllDataForm: " + asuransi);
            startActivity(intent);
        }
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