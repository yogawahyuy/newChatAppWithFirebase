package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.rsip.mobile.Adapter.KeluhanAdapter;
import com.rsip.mobile.MainActivity;
import com.rsip.mobile.Model.KeluhanModel;
import com.rsip.mobile.Model.User;
import com.rsip.mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DaftarKeluhanActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RelativeLayout relEmptyView;
    ArrayList<KeluhanModel> keluhanModels=new ArrayList<>();
    KeluhanAdapter keluhanAdapter;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Keluhan");
    DatabaseReference mRefrence= FirebaseDatabase.getInstance().getReference("Users");
    String hasilNama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_keluhan);
        recyclerView=findViewById(R.id.recy_list_chat);
        recyclerView.setHasFixedSize(true);
        relEmptyView=findViewById(R.id.rel_emptyview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        readKeluhan();
    }

    private void readKeluhan(){
        final String getUid=firebaseUser.getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    KeluhanModel keluhanModel=snapshot.getValue(KeluhanModel.class);
                    if (keluhanModel.getSender().equals(getUid)){
                        String idPembalas=keluhanModel.getIdPembalas();
                        getNama(idPembalas);
                        Log.d("hasilNama", "readKeluhan: "+keluhanModel.getNamaPembalas());
                        keluhanModels.add(keluhanModel);
                        relEmptyView.setVisibility(View.GONE);
                    }else if(!keluhanModel.getSender().equals(getUid)){
                        relEmptyView.setVisibility(View.VISIBLE);
                    }
                    keluhanAdapter =new KeluhanAdapter(DaftarKeluhanActivity.this,keluhanModels);
                    recyclerView.setAdapter(keluhanAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getNama(String idPembalas) {
        mRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    user.setId(dataSnapshot.getKey());
                    if (user.getId().equals(idPembalas)){
                        hasilNama = user.getUsername();
                        Log.d("hasilNama", "onDataChange: "+hasilNama);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DaftarKeluhanActivity.this, MainActivity.class));
        finish();
    }
}
