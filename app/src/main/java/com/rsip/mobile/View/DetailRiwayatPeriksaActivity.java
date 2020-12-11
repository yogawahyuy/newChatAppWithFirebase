package com.rsip.mobile.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.rsip.mobile.Interface.ApiService;
import com.rsip.mobile.MainActivity;
import com.rsip.mobile.R;
import com.rsip.mobile.Utils.Koneksi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailRiwayatPeriksaActivity extends AppCompatActivity {

    TextView noAntrian,kodeBooking,namaPasien,jamDilayani,namaPoli,dokter;
    Intent intent;
    Button btnBatalDaftar,btnKehalamanUtama;
    AlertDialog.Builder dialog;
    String todayDate="";
    Retrofit retrofit;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_periksa);
        findView();
    }

    private void findView(){
        noAntrian=findViewById(R.id.textView_noAntrian);
        kodeBooking=findViewById(R.id.textView_KodeBoking);
        namaPasien=findViewById(R.id.textView_namapasien);
        jamDilayani=findViewById(R.id.textView_jampelayanan);
        namaPoli=findViewById(R.id.textView_namaPoli);
        dokter=findViewById(R.id.textView_namaDokter);
        btnBatalDaftar=findViewById(R.id.btn_batalDaftar);
        btnKehalamanUtama=findViewById(R.id.btn_halamanUtama);
        coordinatorLayout=findViewById(R.id.coordinatorRoot);
        intent=getIntent();
        fillFromIntent();
        btnBatalDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForm();
            }
        });
        btnKehalamanUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailRiwayatPeriksaActivity.this,MainActivity.class));
                finish();
            }
        });
        getDateToTanggalPeriksa();
        initialisingRetrofit();
    }

    private void fillFromIntent(){
        noAntrian.setText(intent.getStringExtra("nomorantrean"));
        kodeBooking.setText(intent.getStringExtra("kodebooking"));
        namaPasien.setText(intent.getStringExtra("namapasien"));
        jamDilayani.setText(intent.getStringExtra("jamdilayani"));
        namaPoli.setText(intent.getStringExtra("namapoli"));
        dokter.setText(intent.getStringExtra("namadokter"));
    }

    private void getDateToTanggalPeriksa(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/YYYY");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date today= Calendar.getInstance().getTime();
        todayDate=dateFormat.format(today);
        Log.d("bookingdate", "getDateToTanggalPeriksa: "+todayDate);

    }
    private void initialisingRetrofit(){
        retrofit=new Retrofit.Builder()
                .baseUrl(Koneksi.URL_BATAL_REG_ONLINE_UMUM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void dialogForm(){
        dialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView;

        dialogView=inflater.inflate(R.layout.custom_dialog_batal_daftar,null);
        dialog.setView(dialogView);
        Button batalDaftar=dialogView.findViewById(R.id.btn_dialogbataldaftar);
        TextView textKodeBooking=dialogView.findViewById(R.id.textView_KodeBoking);
        TextView textTanggalPembatala=dialogView.findViewById(R.id.textView_tanggalPembatalan);
        EditText editTextAlasan=dialogView.findViewById(R.id.edtPembatalan);
        textKodeBooking.setText(intent.getStringExtra("kodebooking"));
        textTanggalPembatala.setText(todayDate);
        batalDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmation(textKodeBooking.getText().toString(),textTanggalPembatala.getText().toString(),editTextAlasan.getText().toString());
            }
        });
        dialog.setCancelable(true);
        dialog.setTitle("Form Pembatalan");

        dialog.show();


    }

    private void dialogConfirmation(String boking, String tanggal, String alasan){
        AlertDialog.Builder dialogs=new AlertDialog.Builder(this);
        dialogs.setMessage("Apakah Anda Yakin Akan Membatalkan Pendaftaran ini?");
        dialogs.setCancelable(false);
        dialogs.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogs, int which) {
                dialogs.dismiss();
            }
        });
        dialogs.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogs, int which) {
                postMessageBatalDaftar(alasan);

            }
        });
        dialogs.show();
    }

    private void postMessageBatalDaftar(String alasan){
        HashMap<String,String> param=new HashMap<>();
        param.put("KODE_BOOKING",intent.getStringExtra("kodebooking"));
        param.put("TGL_BATAL",todayDate);
        //param.put("TGL_BATAL","31/12/2020");
        param.put("ALASAN_BATAL",alasan);
        ApiService apiService=retrofit.create(ApiService.class);
        Call<JsonObject>result=apiService.postBatalDaftarUmum(param);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try{
                    JSONObject root=new JSONObject(response.body().toString());
                    JSONObject respone=root.getJSONObject("response");
                    JSONArray list=respone.getJSONArray("list");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject data=list.getJSONObject(i);
                        Toast.makeText(DetailRiwayatPeriksaActivity.this, data.getString("p_hapus_daftar_antrian_umumx"), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DetailRiwayatPeriksaActivity.this, MainActivity.class));
                        finish();
                        Log.d("data", "onResponse: "+data.getString("p_hapus_daftar_antrian_umumx"));
                    }
                    Log.d("status", "onResponse: ");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

}