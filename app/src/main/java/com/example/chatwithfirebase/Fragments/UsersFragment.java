package com.example.chatwithfirebase.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatwithfirebase.Adapter.UsersAdapter;
import com.example.chatwithfirebase.Model.User;
import com.example.chatwithfirebase.R;
import com.example.chatwithfirebase.View.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private UsersAdapter usersAdapter;
    private List<User> users;
    private Button btnLogout,btnEdit;
    private TextView email,nama,nohp,alamat;
    final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users,container,false);
        email=view.findViewById(R.id.textemail);
        nama=view.findViewById(R.id.textnama);
        nohp=view.findViewById(R.id.texthp);
        alamat=view.findViewById(R.id.textalamat);

        btnEdit=view.findViewById(R.id.edit_profilebtn);
        btnLogout=view.findViewById(R.id.logout_profilebtn);
        if (firebaseUser==null){
            btnLogout.setText("Login");
            btnEdit.setVisibility(View.GONE);
        }else{
            btnLogout.setText("LOG OUT");
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser!=null) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getContext(), "Anda Berhasil Logout", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), StartActivity.class));
                }else{
                    startActivity(new Intent(getContext(), StartActivity.class));
                }
            }
        });
        users=new ArrayList<>();
        readUsers();
        return view;
    }

    private void readUsers() {
        if (firebaseUser!=null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (!user.getUsername().isEmpty()) {
                        nama.setText(user.getUsername());
                    }
                    if (!user.getEmail().isEmpty()) {
                        email.setText(user.getEmail());
                    }
                    if (!user.getNumberPhone().isEmpty()) {
                        nohp.setText(user.getNumberPhone());
                    }
                    if (!user.getUsername().isEmpty()) {
                        alamat.setText(user.getAlamat());
                    } else {
                        alamat.setText("alamat disini");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
}
