package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rsip.mobile.Model.GlideApp;
import com.rsip.mobile.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailDokterActivity extends AppCompatActivity {

    Button btnBagikanDokter;
    Intent intent;
    CircleImageView profilePicture;
    TextView namaDokter,spesialDokter,hariDokter,jamDokter,statusDokter,ketDokter;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokter);

        findView();
        intent=getIntent();
        fillDataFromIntent();
        getPhotoDokter();

        btnBagikanDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillToIntent();
            }
        });
    }

    private void findView(){
        btnBagikanDokter=findViewById(R.id.btn_bagikandokter);
        profilePicture=findViewById(R.id.profile_picture);
        namaDokter=findViewById(R.id.textnamaDokter);
        spesialDokter=findViewById(R.id.text_spesialisdokter);
        hariDokter=findViewById(R.id.text_haridokter);
        jamDokter=findViewById(R.id.text_jamdokter);
        statusDokter=findViewById(R.id.text_statusDokter);
        ketDokter=findViewById(R.id.textketeranganDokter);
    }

    private void fillDataFromIntent(){
        String harikedua=intent.getStringExtra("harikedua");
        if (TextUtils.isEmpty(harikedua)) {
            namaDokter.setText(intent.getStringExtra("namadokter"));
            spesialDokter.setText(intent.getStringExtra("spesial"));
            hariDokter.setText(intent.getStringExtra("hari"));
            jamDokter.setText(intent.getStringExtra("jam"));
            statusDokter.setText(intent.getStringExtra("status"));
            ketDokter.setText(intent.getStringExtra("ket"));
        }else{
            namaDokter.setText(intent.getStringExtra("namadokter"));
            spesialDokter.setText(intent.getStringExtra("spesial"));
            String hari=intent.getStringExtra("hari");
            hari+=" dan ";
            hari+=intent.getStringExtra("harikedua");
            hariDokter.setText(hari);

            String jam=intent.getStringExtra("jam");
            jam+=" dan ";
            jam+=intent.getStringExtra("jamkedua");
            jamDokter.setText(jam);
            statusDokter.setText(intent.getStringExtra("status"));
            ketDokter.setText(intent.getStringExtra("ket"));
        }
    }
    private void getPhotoDokter(){
        storageReference= FirebaseStorage.getInstance().getReference("ProfilePicture/"+intent.getStringExtra("key")+".jpg");
        Log.d("detaildokter", "getPhotoDokter: "+storageReference);
        GlideApp.with(this).load(storageReference).into(profilePicture);

    }
    private void fillToIntent(){
        Intent intent1=new Intent(DetailDokterActivity.this,ShareDokterActivity.class);
        intent1.putExtra("key",intent.getStringExtra("key"));
        intent1.putExtra("id",intent.getStringExtra("id"));
        intent1.putExtra("namadokter",intent.getStringExtra("namadokter"));
        intent1.putExtra("spesial",intent.getStringExtra("spesial"));
        intent1.putExtra("hari",intent.getStringExtra("hari"));
        intent1.putExtra("jam",intent.getStringExtra("jam"));
        intent1.putExtra("status",intent.getStringExtra("status"));
        intent1.putExtra("ket",intent.getStringExtra("ket"));
        intent1.putExtra("harikedua",intent.getStringExtra("harikedua"));
        intent1.putExtra("jamkedua",intent.getStringExtra("jamkedua"));
        startActivity(intent1);
        Log.d("detail", "fillToIntent: "+intent.getStringExtra("namadokter"));
    }
}