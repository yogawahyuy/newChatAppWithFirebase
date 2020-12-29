package com.rsip.mobile.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rsip.mobile.Adapter.UsersAdapter;
import com.rsip.mobile.Model.SearchModel;
import com.rsip.mobile.Model.User;
import com.rsip.mobile.R;
import com.rsip.mobile.View.DaftarKeluhanActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;



public class ChatsFragment extends Fragment {
    private View v;

    UsersAdapter usersAdapter;
    Button callRsi,chatRsi,laporkan,lihatKeluhan;
    EditText editTextTanggal,editTextNama,editTextKeluhan;
    Spinner spinerKeluhan,spinerUnit;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    AlertDialog.Builder dialog,dialogCallCs;
    LayoutInflater inflater,inflaterCallCs;
    View dialogView,dialogViewCallCs;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_chats,container,false);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
        reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());}

        callRsi=v.findViewById(R.id.btn_hubcs);
        lihatKeluhan=v.findViewById(R.id.btn_lihatChat);
        chatRsi=v.findViewById(R.id.btn_chatcs);
        callRsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogFormCallCenter();
            }
        });
        chatRsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser==null){
                    Toast.makeText(getContext(), "Anda Harus Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    dialogForm();
                    readNamaUser();
                }
            }
        });
        lihatKeluhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser != null) {
                    startActivity(new Intent(getContext(), DaftarKeluhanActivity.class));
                }else {
                    Toast.makeText(getContext(), "Anda Harus Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
    private void dialogForm(){
        dialog=new AlertDialog.Builder(getContext());
        inflater=getLayoutInflater();
        dialogView=inflater.inflate(R.layout.custom_dialog_chat_cs,null);
        dialog.setView(dialogView);
        laporkan=dialogView.findViewById(R.id.btn_laporkan);
        editTextTanggal=dialogView.findViewById(R.id.edttext_tgl);
        editTextNama=(EditText)dialogView.findViewById(R.id.edttext_name);
        spinerKeluhan=dialogView.findViewById(R.id.sp_keluhan);
        spinerUnit=dialogView.findViewById(R.id.sp_unit);
        editTextKeluhan=dialogView.findViewById(R.id.edttext_keluhan);
        editTextTanggal.setInputType(InputType.TYPE_NULL);
        editTextTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        dialog.setCancelable(true);
        dialog.setTitle("Form Pengaduan");
        laporkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendKeluhan(firebaseUser.getUid(),editTextNama.getText().toString(),editTextTanggal.getText().toString(),spinerKeluhan.getSelectedItem().toString(),spinerUnit.getSelectedItem().toString(),editTextKeluhan.getText().toString());
               startActivity(new Intent(getContext(),DaftarKeluhanActivity.class));
               closeFragment();
            }
        });
        dialog.show();
    }
    private void dialogFormCallCenter(){
        Button btnIgd,btnAmbulance,btnKomplain;
        dialogCallCs=new AlertDialog.Builder(getContext());
        inflaterCallCs=getLayoutInflater();
        dialogViewCallCs=inflaterCallCs.inflate(R.layout.custom_dialog_call_center,null);
        dialogCallCs.setView(dialogViewCallCs);
        btnIgd=dialogViewCallCs.findViewById(R.id.btn_call_igd);
        btnAmbulance=dialogViewCallCs.findViewById(R.id.btn_call_ambulance);
        btnKomplain=dialogViewCallCs.findViewById(R.id.btn_call_komplain);
        dialogCallCs.setCancelable(true);
        dialogCallCs.setTitle("Call Center RSI");
        dialogCallCs.setPositiveButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        btnIgd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:(0281)630019"));
                startActivity(callIntent);
            }
        });
        btnAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:085875035300"));
                startActivity(callIntent);
            }
        });
        btnKomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:085875035300"));
                startActivity(callIntent);
            }
        });
        dialogCallCs.show();
    }
    private void closeFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private void showDatePicker(){
        final Calendar cldr=Calendar.getInstance();
        int day=cldr.get(Calendar.DAY_OF_MONTH);
        int month=cldr.get(Calendar.MONTH);
        int year=cldr.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextTanggal.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        },year,month,day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }
    private void readNamaUser(){
        if (firebaseUser!=null){
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                        editTextNama.setText(user.getUsername());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void sendKeluhan(String senderId,String nama,String tanggal,String kategori,String unit,String keluhan){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap=new HashMap<>();

        hashMap.put("sender",senderId);
        hashMap.put("nama",nama);
        hashMap.put("tanggal",tanggal);
        hashMap.put("kategori",kategori);
        hashMap.put("unit",unit);
        hashMap.put("keluhan",keluhan);
        hashMap.put("statusBalas","belum");
        hashMap.put("pesanBalasan","belum dibalas");
        hashMap.put("idPembalas","belum ada");
        hashMap.put("namaPembalas","Belum Ada");


        reference.child("Keluhan").push().setValue(hashMap);
    }

}
