package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rsip.mobile.BuildConfig;
import com.rsip.mobile.Model.GlideApp;
import com.rsip.mobile.R;

import java.io.File;
import java.io.FileOutputStream;

public class ShareDokterActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    Button btnShare;
    ImageView profilePicture;
    TextView namaDokter,spesialisDokter,hariPertama,jamPertama,hariKedua,jamKedua;
    LinearLayout linearLayout;
    Intent intent;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_dokter);

       intent=getIntent();
        findView();
       //getDataFromIntent();
       //getPhotoDokter();
        fillDataFromIntent();
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });
    }

    private void findView(){
        relativeLayout=findViewById(R.id.relPicture);
        btnShare=findViewById(R.id.btnShare);
        profilePicture=findViewById(R.id.profile_picture);
        namaDokter=findViewById(R.id.text_namadokter);
        spesialisDokter=findViewById(R.id.text_spesialisdokter);
        hariPertama=findViewById(R.id.text_haridokter);
        hariKedua=findViewById(R.id.text_haridokterkedua);
        jamPertama=findViewById(R.id.text_jamdokter);
        jamKedua=findViewById(R.id.text_jamdokterkedua);
        linearLayout=findViewById(R.id.waktu_kedua);
        Log.d("isi inten", "findView: "+intent.getStringExtra("nip_dokterx"));
        getPhotoDokter();
    }
    private void fillDataFromIntent(){
        namaDokter.setText(intent.getStringExtra("nm_dokterx"));
        spesialisDokter.setText(intent.getStringExtra("nm_poliklinikx"));
        hariPertama.setText(intent.getStringExtra("tglx"));
        //jamPertama.setText(intent.getStringExtra("jam"));
        hariKedua.setText(intent.getStringExtra("harix"));
        jamKedua.setText(intent.getStringExtra("jam"));
    }
//    private void getDataFromIntent(){
//        String harikeduas=intent.getStringExtra("harikedua");
//        if (!TextUtils.isEmpty(harikeduas)) {
//            namaDokter.setText(intent.getStringExtra("namadokter"));
//            spesialisDokter.setText(intent.getStringExtra("spesial"));
//            hariPertama.setText(intent.getStringExtra("hari"));
//            jamPertama.setText(intent.getStringExtra("jam"));
//        }else{
//            linearLayout.setVisibility(View.VISIBLE);
//            namaDokter.setText(intent.getStringExtra("namadokter"));
//            spesialisDokter.setText(intent.getStringExtra("spesial"));
//            hariPertama.setText(intent.getStringExtra("hari"));
//            jamPertama.setText(intent.getStringExtra("jam"));
//            hariKedua.setText(intent.getStringExtra("harikedua"));
//            jamKedua.setText(intent.getStringExtra("jamkedua"));
//        }
//
//    }
    private void getPhotoDokter(){
//        storageReference= FirebaseStorage.getInstance().getReference("FotoDokter/"+intent.getStringExtra("nip_dokterx")+".jpg");
//        Log.d("detaildokter", "getPhotoDokter: "+storageReference);
//        GlideApp.with(this).load(storageReference).into(profilePicture);
        String url="http://103.255.241.124:5758/fotodokter/"+intent.getStringExtra("nip_dokterx")+".jpg";
        GlideApp.with(this).load(url).into(profilePicture);

    }

    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapFromView(View view){
        Bitmap returnBitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(returnBitmap);
        Drawable bgDrawable=view.getBackground();
        if (bgDrawable!=null)bgDrawable.draw(canvas);
        else canvas.drawColor(android.R.color.white);
        view.draw(canvas);
        return returnBitmap;
    }
    private void shareImage(){
        Bitmap bitmap = getBitmapFromView(relativeLayout);
        //String fileName="RsiMobile-"+surahNo.getText().toString()+".jpg";
        String filePath=this.getFilesDir().getPath().toString() + "/RsiMobile-"+intent.getStringExtra("nm_dokterx")+".PNG";
        File file=new File(filePath);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            file.setReadable(true,false);
            Uri uri= FileProvider.getUriForFile(ShareDokterActivity.this, BuildConfig.APPLICATION_ID+".provider",file);
            Intent intentShare=new Intent(Intent.ACTION_SEND);
            intentShare.putExtra(Intent.EXTRA_STREAM,uri);
            intentShare.setType("image/jpg");
            startActivity(Intent.createChooser(intentShare,"Bagikan Ke"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}