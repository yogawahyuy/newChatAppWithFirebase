package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rsip.mobile.R;

public class LupaPasswordActivity extends AppCompatActivity {
    MaterialEditText editText_email;
    Button cariEmailBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        editText_email=findViewById(R.id.editText_email);
        cariEmailBtn=findViewById(R.id.cariEmailBtn);
        auth=FirebaseAuth.getInstance();
        resetPassword();
    }

    private void resetPassword(){
        cariEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editText_email.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LupaPasswordActivity.this, "Email Harus Dimasukan", Toast.LENGTH_SHORT).show();
                }

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LupaPasswordActivity.this, "Cek Email Anda", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LupaPasswordActivity.this,LoginActivity.class));
                        }else{
                            Toast.makeText(LupaPasswordActivity.this, "Email Tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}