package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rsip.mobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class BookingActivity extends AppCompatActivity {
    TextView tvnorm;
    EditText noRM,tanggalPeriksa,jamPeriksa,ruangPeriksa,nomerRM;
    Spinner poliTuju,dokterTuju,pembayaranSpinner;
    LinearLayout linRM,linReg;
    String stNorm;
    Button btnDaftar;
    ArrayAdapter<String> arrayAdapter;
    String dokterMata[]={"Muhamad Rifqy Setyanto, Dr., dr., Sp.M(K)Retina"};
    String dokterBedah[]={"M. Hidayat Budi Kusumo, dr., Sp.B., MSi. Med., ST.","Yuhantoro Budi H.S, dr., Sp.B., M.Kes."};
    String dokterSyaraf[]={"Muttaqien Pramudigdo, dr., Sp.S.","Hernawan, dr., Sp.S"};
    String pembayaran[]={"BPJS","UMUM"};
    String hari,bulan,tahun;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    long maxid=0;
    String jenisPendaftaran="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        findView();
    }
    private void findView(){
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
        btnDaftar=findViewById(R.id.btn_Daftar);
        linRM=findViewById(R.id.lin_norm);
        linReg=findViewById(R.id.lin_regpasien);
        nomerRM=findViewById(R.id.edittxt_NoRm);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(BookingActivity.this,StatusBookingActivity.class));
                daftarAntrian();
            }
        });
        tanggalPeriksa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        //getDateToTanggalPeriksa();
        getTimeNow();

        //adapter pendaftaran
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,pembayaran);
        pembayaranSpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
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

    private void checkNoRm(){
        stNorm=noRM.getText().toString();
        tvnorm.setText(stNorm);
        linReg.setVisibility(View.VISIBLE);
        linRM.setVisibility(View.GONE);

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
                hari=String.valueOf(dayOfMonth);
                bulan=String.valueOf(month+1);
                tahun=String.valueOf(year);
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

    private void daftarAntrian(){
        String noRM=nomerRM.getText().toString();
        String tglPeriksa=tanggalPeriksa.getText().toString();
        String jamPeriksaa=jamPeriksa.getText().toString();
        String poliDiTuju=poliTuju.getSelectedItem().toString();
        String drTuju=dokterTuju.getSelectedItem().toString();
        String rPeriksa=ruangPeriksa.getText().toString();
        String pembayaran=pembayaranSpinner.getSelectedItem().toString();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Pendaftaran");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String noAntrian;
        if (pembayaranSpinner.getSelectedItem().toString().equals("BPJS")){
            jenisPendaftaran="BPJS";
             noAntrian="B"+(maxid+1);
        }else{
            jenisPendaftaran="UMUM";
            noAntrian="A"+(maxid+1);
        }
        saveToFirebase(noRM,tglPeriksa,jamPeriksaa,poliDiTuju,drTuju,rPeriksa,pembayaran,noAntrian);

    }

    private void saveToFirebase(String noRm,String tglPeriksa,String jamPeriksa,String poliDiTuju,String drTuju,String rPeriksa,String pembayaran,String noAntrian){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();

        hashMap.put("idSender",firebaseUser.getUid());
        hashMap.put("noRM",noRm);
        hashMap.put("tglPeriksa",tglPeriksa);
        hashMap.put("jamPeriksa",jamPeriksa);
        hashMap.put("poliTuju",poliDiTuju);
        hashMap.put("drTuju",drTuju);
        hashMap.put("rPeriksa",rPeriksa);
        hashMap.put("Pembayaran",pembayaran);
        hashMap.put("noAntrian",noAntrian);

        Log.d("tanggal", "saveToFirebase: "+hari);
        Log.d("tanggal", "saveToFirebase: "+bulan);
        Log.d("tanggal", "saveToFirebase: "+tahun);

        reference.child("Pendaftaran").child(jenisPendaftaran).child(tahun).child(bulan).child(hari).push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(BookingActivity.this, "Pendaftaran Anda Berhasil", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BookingActivity.this,StatusBookingActivity.class);
                intent.putExtra("noAntrian",noAntrian);
                intent.putExtra("tgl",hari+"/"+bulan+"/"+tahun);
                startActivity(intent);
            }
        });
    }
}
