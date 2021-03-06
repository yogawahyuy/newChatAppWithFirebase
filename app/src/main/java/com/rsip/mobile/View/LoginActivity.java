package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.rsip.mobile.CustomView.CustomProgressDialog;
import com.rsip.mobile.MainActivity;
import com.rsip.mobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText email,password;
    Button btnLogin;
    CustomProgressDialog dialog;
    FirebaseAuth auth;
    LinearLayout linLupaPassword,linGotoDaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnlogin);
        linLupaPassword=findViewById(R.id.lin_lupaPassword);
        linGotoDaftar=findViewById(R.id.lin_goto_daftar);
        dialog=new CustomProgressDialog(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              toLogin();
            }
        });
        linLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,LupaPasswordActivity.class));
            }
        });
        linGotoDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
    private void toLogin(){
        String txtEmail=email.getText().toString();
        String txtPassword=password.getText().toString();

        if (TextUtils.isEmpty(txtEmail)||TextUtils.isEmpty(txtPassword)){
            Toast.makeText(LoginActivity.this, "Isi semua form", Toast.LENGTH_SHORT).show();
        }else {
            dialog.showDialog();
            auth.signInWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user=auth.getCurrentUser();
                        if (user.isEmailVerified()){
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            dialog.hideDialog();
                            finish();
                        }else{
                            dialog.hideDialog();
                            Toast.makeText(LoginActivity.this, "Email Belum Diverifikasi", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(LoginActivity.this, "Email atau Password Salah", Toast.LENGTH_SHORT).show();
                        dialog.hideDialog();
                    }
                }
            });
        }
    }
}
