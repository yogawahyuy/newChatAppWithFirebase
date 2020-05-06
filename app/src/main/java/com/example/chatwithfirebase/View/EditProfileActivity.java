package com.example.chatwithfirebase.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chatwithfirebase.Model.User;
import com.example.chatwithfirebase.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class EditProfileActivity extends AppCompatActivity {
    EditText nama,email,noHp,alamat;
    Spinner spinerJk;
    CircleImageView profilePhoto;
    Button buttonSave;
    FirebaseUser firebaseUser;
    DatabaseReference dbreference;
    File compresed;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    Bitmap bitmap;
    ProgressBar progressBar;
    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        nama=findViewById(R.id.editNamaProfile);
        email=findViewById(R.id.editEmailProfile);
        noHp=findViewById(R.id.editNohpProfile);
        alamat=findViewById(R.id.editAlamatProfile);
        spinerJk=findViewById(R.id.jk_editprofile);
        profilePhoto=findViewById(R.id.profile_picture);
        buttonSave=findViewById(R.id.btn_simpan);
        progressBar=findViewById(R.id.progress_editprofile);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        dbreference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        readProfile();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK){
            filePath=data.getData();

            try{
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                profilePhoto.setImageBitmap(bitmap);
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readProfile(){
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                nama.setText(user.getUsername());
                email.setText(user.getEmail());
                noHp.setText(user.getNumberPhone());
                alamat.setText(user.getAlamat());
                if (user.getJk()==null)
                    spinerJk.setSelection(0);
                else if (user.getJk().equals("1"))spinerJk.setSelection(0);
                else spinerJk.setSelection(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void updateProfile(){
        User user=new User();
        int spinerID;
        if (spinerJk.getSelectedItemPosition()==0){
            spinerID=1;
        }else{
            spinerID=2;
        }
        user.setId(firebaseUser.getUid());
        user.setImageURL(filePath.toString());
        user.setUsername(nama.getText().toString());
        user.setEmail(email.getText().toString());
        user.setNumberPhone(noHp.getText().toString());
        user.setJk(String.valueOf(spinerID));
        user.setAlamat(alamat.getText().toString());
        dbreference.setValue(user).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditProfileActivity.this, "Profile Berhasil diUpdate", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileActivity.this,StartActivity.class));
                finish();
            }
        });
        uploadIntoFirebase();

    }
    private void getImageFromCamera(){

    }
    private void selectImage(){
        Intent intent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Pilih..."),PICK_IMAGE_REQUEST);
    }
    private void uploadIntoFirebase(){
        if (filePath!=null){
            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Mengupload foto");
            progressDialog.show();
            profilePhoto.setDrawingCacheEnabled(true);
            profilePhoto.buildDrawingCache();
            Bitmap bitmaps=((BitmapDrawable) profilePhoto.getDrawable()).getBitmap();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            StorageReference ref = storageReference.child("ProfilePicture/"+firebaseUser.getUid()+".jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] bytes=baos.toByteArray();
            String namafile=firebaseUser.getUid()+".jpg";
            String namafolder="ProfilePicture/"+namafile;

            UploadTask uploadTask= storageReference.child(namafolder).putBytes(bytes);
//            uploadTask.continueWith(new Continuation<UploadTask.TaskSnapshot,Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()){
//                        throw task.getException();
//                    }
//                    return storageReference.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()){
//                        Uri downloadUri=task.getResult();
//                        String mUri=downloadUri.toString();
//                        HashMap<String ,Object> map=new HashMap<>();
//                        map.put("imageURL",mUri);
//                        dbreference.updateChildren(map);
//                        progressDialog.dismiss();
//                    }else{
//                        Toast.makeText(EditProfileActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                }
//            }).addOnFailureListener()
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, "Uploading Berhasil", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, "Uploading Gagal", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                }
            });
        }
    }
}
