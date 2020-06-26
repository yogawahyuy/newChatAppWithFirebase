package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rsip.mobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BookingActivity extends AppCompatActivity {
    TextView tvnorm;
    EditText noRM,tanggalPeriksa,jamPeriksa,ruangPeriksa;
    Spinner poliTuju,dokterTuju,pembayaranSpinner;
    LinearLayout linRM,linReg;
    String stNorm;
    Button btnDaftar;
    ArrayAdapter<String> arrayAdapter;
    String dokterMata[]={"Muhamad Rifqy Setyanto, Dr., dr., Sp.M(K)Retina"};
    String dokterBedah[]={"M. Hidayat Budi Kusumo, dr., Sp.B., MSi. Med., ST.","Yuhantoro Budi H.S, dr., Sp.B., M.Kes."};
    String dokterSyaraf[]={"Muttaqien Pramudigdo, dr., Sp.S.","Hernawan, dr., Sp.S"};
    String pembayaran[]={"BPJS","UMUM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        tvnorm=findViewById(R.id.tv_rm);
        noRM=findViewById(R.id.edtnorm);
        tanggalPeriksa=findViewById(R.id.edittxt_tanggalperiksa);
        jamPeriksa=findViewById(R.id.edittxt_jamperiksa);
        ruangPeriksa=findViewById(R.id.edittxt_ruangperiksa);
        jamPeriksa.setInputType(InputType.TYPE_NULL);
        tanggalPeriksa.setInputType(InputType.TYPE_NULL);
        ruangPeriksa.setInputType(InputType.TYPE_NULL);
        poliTuju=findViewById(R.id.spiner_poli);
        dokterTuju=findViewById(R.id.spiner_dokter);
        pembayaranSpinner=findViewById(R.id.spiner_Pembayaran);
        btnDaftar=findViewById(R.id.daftarbtn);
        linRM=findViewById(R.id.lin_norm);
        linReg=findViewById(R.id.lin_regpasien);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNoRm();
            }
        });
        tanggalPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        getDateToTanggalPeriksa();
        getTimeNow();

        //adapter pendaftaran
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,pembayaran);
        pembayaranSpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    private void checkNoRm(){
        stNorm=noRM.getText().toString();
        tvnorm.setText(stNorm);
        linReg.setVisibility(View.VISIBLE);
        linRM.setVisibility(View.GONE);
        poliTuju.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void spinnerData(int pos){
         if (pos==2){
            arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,dokterBedah);
            dokterTuju.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
            Log.d("posisi booking", "spinnerData: "+pos);
            ruangPeriksa.setText("Poli Bedah");

        }else if (pos==9){
            arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,dokterMata);
            dokterTuju.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
            Log.d("posisi booking", "spinnerData: "+pos);
            ruangPeriksa.setText("Poli Mata");

         }else {
             arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.doktersyaraf));
             dokterTuju.setAdapter(arrayAdapter);
             arrayAdapter.notifyDataSetChanged();
             Log.d("posisi booking", "spinnerData: "+pos);
             ruangPeriksa.setText("Poli UMUM");

         }

    }
    private void showDatePicker(){
        final Calendar cldr=Calendar.getInstance();
        int day=cldr.get(Calendar.DAY_OF_MONTH);
        int month=cldr.get(Calendar.MONTH);
        int year=cldr.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                tanggalPeriksa.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        },year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000+(1000*60*60*24*7));
        datePickerDialog.show();
    }
    private void getDateToTanggalPeriksa(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date today=Calendar.getInstance().getTime();
        String todayDate=dateFormat.format(today);
        Log.d("bookingdate", "getDateToTanggalPeriksa: "+todayDate);
        tanggalPeriksa.setText(todayDate);
    }
    private void getTimeNow(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date timeNow=Calendar.getInstance().getTime();
        String time=dateFormat.format(timeNow);
        Log.d("timeBoking", "getTimeNow: "+time);
        jamPeriksa.setText(time);
    }
}
