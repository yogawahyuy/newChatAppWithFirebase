package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rsip.mobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout fullname,email,password,numberPhone,confpassword,alamat;
    Button btn_register;
    Spinner spinner;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        Toolbar toolbar=findViewById(R.id.toolsbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Register");
//        getSupportActionBar() .setDisplayShowHomeEnabled(true);

        fullname=findViewById(R.id.nama_lengkap_layout);
        email=findViewById(R.id.emaillayout);
        numberPhone=findViewById(R.id.nomorhplayout);
        password=findViewById(R.id.passwordlayout);
        confpassword=findViewById(R.id.confpasswordlayout);
        alamat=findViewById(R.id.alamatlayout);
        btn_register=findViewById(R.id.btnregister);
        auth=FirebaseAuth.getInstance();
        spinner=findViewById(R.id.spiner_register);
        fullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname.setError("nama panjang ya");
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername=fullname.getEditText().getText().toString();
                String txtEmail=email.getEditText().getText().toString();
                String txtPassword=password.getEditText().getText().toString();
                String txtNumberPhone=numberPhone.getEditText().getText().toString();
                String txtConfPassword=confpassword.getEditText().getText().toString();
                String txtAlamat=alamat.getEditText().getText().toString();

                if (TextUtils.isEmpty(txtUsername)||TextUtils.isEmpty(txtEmail)||TextUtils.isEmpty(txtPassword)||TextUtils.isEmpty(txtNumberPhone)||TextUtils.isEmpty(txtConfPassword)){
                    Toast.makeText(RegisterActivity.this, "Isi Semua Form", Toast.LENGTH_SHORT).show();
                }else if (txtPassword.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
                }else if(!txtPassword.equals(txtConfPassword)){
                    Toast.makeText(RegisterActivity.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                }else{
                    register(txtUsername,txtEmail,txtNumberPhone,txtPassword,txtAlamat,spinner);
                }
            }
        });
    }

    private void register(final String username, final String email, final String numberPhone, final String password, final String alamat, final Spinner spinner){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    int spinerID;
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    assert firebaseUser != null;
                    String uid=firebaseUser.getUid();
                    if (spinner.getSelectedItemPosition()==0){
                        spinerID=1;
                    }else{
                        spinerID=2;
                    }
                    reference= FirebaseDatabase.getInstance().getReference("Users").child(uid);
                    HashMap<String, String > hashMap=new HashMap<>();
                    hashMap.put("id",uid);
                    hashMap.put("username",username);
                    hashMap.put("email",email);
                    hashMap.put("numberPhone",numberPhone);
                    hashMap.put("alamat",alamat);
                    hashMap.put("jk",String.valueOf(spinerID));
                    hashMap.put("ImageURL","Default");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "Cek email untuk verifikasi", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Gagal mengirim email verifikasi", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(RegisterActivity.this, "You can't register with Email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
