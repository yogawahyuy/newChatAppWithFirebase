package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.rsip.mobile.Adapter.KeluhanAdapter;
import com.rsip.mobile.Model.KeluhanModel;
import com.rsip.mobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaftarKeluhanActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RelativeLayout relEmptyView;
    ArrayList<KeluhanModel> keluhanModels=new ArrayList<>();
    KeluhanAdapter keluhanAdapter;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Keluhan");


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
                        keluhanModels.add(keluhanModel);
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
}
