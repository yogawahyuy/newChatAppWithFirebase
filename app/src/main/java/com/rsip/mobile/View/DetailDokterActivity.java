package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
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
    String jam;

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
        namaDokter.setText(intent.getStringExtra("nm_dokterx"));
        spesialDokter.setText(intent.getStringExtra("nm_poliklinikx"));
        jam=intent.getStringExtra("jam_mulaix");
        jam+=" - ";
        jam+=intent.getStringExtra("jam_selesaix");
        jamDokter.setText("JAM "+jam);
        hariDokter.setText("HARI "+intent.getStringExtra("harix"));
        statusDokter.setText(intent.getStringExtra("status"));
        ketDokter.setText(intent.getStringExtra("ket"));
        statusDokter.setText(intent.getStringExtra("tglx"));

//        if (TextUtils.isEmpty(harikedua)) {
//            namaDokter.setText(intent.getStringExtra("nm_dokterx"));
//            spesialDokter.setText(intent.getStringExtra("nm_poliklinikx"));
//            hariDokter.setText(intent.getStringExtra("harix"));
//            jamDokter.setText(intent.getStringExtra("jam_mulaix"));
//            statusDokter.setText(intent.getStringExtra("status"));
//            ketDokter.setText(intent.getStringExtra("ket"));
//        }
//        else{
//            namaDokter.setText(intent.getStringExtra("namadokter"));
//            spesialDokter.setText(intent.getStringExtra("spesial"));
//            String hari=intent.getStringExtra("hari");
//            hari+=" dan ";
//            hari+=intent.getStringExtra("harikedua");
//            hariDokter.setText(hari);
//
//            String jam=intent.getStringExtra("jam");
//            jam+=" dan ";
//            jam+=intent.getStringExtra("jamkedua");
//            jamDokter.setText(jam);
//            statusDokter.setText(intent.getStringExtra("status"));
//            ketDokter.setText(intent.getStringExtra("ket"));
//        }
    }
    private void getPhotoDokter(){
//        storageReference = FirebaseStorage.getInstance().getReference("FotoDokter/" + intent.getStringExtra("nip_dokterx") + ".jpg");
//        Log.d("detaildokter", "getPhotoDokter: " + storageReference);
//
//        GlideApp.with(this).load(storageReference).into(profilePicture).onLoadFailed(getDrawable(R.drawable.dokter2));
        String url="http://103.255.241.124:5758/fotodokter/"+intent.getStringExtra("nip_dokterx")+".jpg";
        GlideApp.with(this).load(url).into(profilePicture);
        //GlideApp.with(this).load("gs://rsi-mobile.appspot.com/FotoDokter/"+intent.getStringExtra("nip_dokterx")+".jpg").into(profilePicture);

//        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                GlideApp.with(DetailDokterActivity.this).load(storageReference).into(profilePicture);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                GlideApp.with(DetailDokterActivity.this).load(R.drawable.riwayatpemeriksaan2).into(profilePicture);
//            }
//        });

    }
    private void fillToIntent(){
        Intent intent1=new Intent(DetailDokterActivity.this,ShareDokterActivity.class);
        intent1.putExtra("key",intent.getStringExtra("key"));
        intent1.putExtra("id",intent.getStringExtra("id"));
        intent1.putExtra("nm_dokterx",intent.getStringExtra("nm_dokterx"));
        intent1.putExtra("nm_poliklinikx",intent.getStringExtra("nm_poliklinikx"));
        intent1.putExtra("harix",intent.getStringExtra("harix"));
        intent1.putExtra("jam",jam);
        intent1.putExtra("tglx",intent.getStringExtra("tglx"));
        intent1.putExtra("ket",intent.getStringExtra("ket"));
        intent1.putExtra("harikedua",intent.getStringExtra("harikedua"));
        intent1.putExtra("jamkedua",intent.getStringExtra("jamkedua"));
        intent1.putExtra("nip_dokterx",intent.getStringExtra("nip_dokterx"));
        startActivity(intent1);
        Log.d("detail", "fillToIntent: "+intent.getStringExtra("namadokter"));
    }
}