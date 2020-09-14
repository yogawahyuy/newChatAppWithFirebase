package com.rsip.mobile.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rsip.mobile.Adapter.UsersAdapter;
import com.rsip.mobile.Model.GlideApp;
import com.rsip.mobile.Model.User;
import com.rsip.mobile.R;
import com.rsip.mobile.View.EditProfileActivity;
import com.rsip.mobile.View.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private UsersAdapter usersAdapter;
    private List<User> users;
    private Button btnLogout,btnEdit;
    CircleImageView profilePicture;
    ProgressDialog progressDialog;
    private TextView email,nama,nohp,alamat,jeniskelamin;
    StorageReference storageReference;
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
        jeniskelamin=view.findViewById(R.id.textjk);
        profilePicture=view.findViewById(R.id.profile_picture);
        progresDialog();
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
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
                getActivity().finish();

            }
        });
        if (firebaseUser!=null) {
            storageReference = FirebaseStorage.getInstance().getReference("ProfilePicture/" + firebaseUser.getUid() + ".jpg");
        }
        users=new ArrayList<>();
        readUsers();
       // GlideApp.with(getContext()).load(storageReference).into(profilePicture);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        readUsers();
        //GlideApp.with(getContext()).load(storageReference).into(profilePicture);
    }

    private void readUsers() {
        if (firebaseUser!=null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            storageReference= FirebaseStorage.getInstance().getReference("ProfilePicture/"+firebaseUser.getUid()+".jpg");
            //Glide.with(getContext()).load(storageReference).into(profilePicture);
            Log.d("userpregment", "readUsers: "+storageReference);
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
                    if (!user.getJk().isEmpty()){
                        if (user.equals("1")){
                            jeniskelamin.setText("Laki-laki");
                        }else{
                            jeniskelamin.setText("Perempuan");
                        }
                    }
                    if (!user.getAlamat().isEmpty()) {
                        alamat.setText(user.getAlamat());
                    }else {
                        alamat.setText("alamat disini");
                    }if (!user.getImageURL().equals("Default")){
                        GlideApp.with(getContext()).load(storageReference).into(profilePicture);
                        Log.d("isi storage", "onDataChange: "+storageReference);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
    private void progresDialog(){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Sedang Mengambil Info Anda");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
