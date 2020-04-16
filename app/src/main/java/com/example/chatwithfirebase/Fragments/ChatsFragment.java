package com.example.chatwithfirebase.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chatwithfirebase.Adapter.UsersAdapter;
import com.example.chatwithfirebase.Model.SearchModel;
import com.example.chatwithfirebase.Model.User;
import com.example.chatwithfirebase.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;


public class ChatsFragment extends Fragment {
    private View v;

    UsersAdapter usersAdapter;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_chats,container,false);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        FloatingActionButton fab=v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleSearchDialogCompat(getContext(), "Pengguna...", "Nama pengguna...", null, initData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, Searchable item, int position) {
                        Toast.makeText(getContext(), ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        return v;
    }
        private ArrayList<SearchModel> initData(){
        ArrayList<SearchModel> items=new ArrayList<>();
        items.add(new SearchModel("test"));
        items.add(new SearchModel("satu dua"));
        items.add(new SearchModel("tiga"));
        items.add(new SearchModel("empat lima"));
        items.add(new SearchModel("enam"));
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                items.clear();
//                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    User user=snapshot.getValue(User.class);
//                    assert user!=null;
//                    assert firebaseUser!=null;
//                    if (!user.getId().equals(firebaseUser.getUid())){
//                        items.add(user);
//                    }
//                }
//                RecyclerView recyclerView=v.findViewById(R.id.recycler);
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                recyclerView.setAdapter(usersAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        return items;
    }

}
