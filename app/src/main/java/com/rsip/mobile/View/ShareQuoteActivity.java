package com.rsip.mobile.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rsip.mobile.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;

public class ShareQuoteActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;

    TextView textQuote,textAuthor,surahNo,ayahNo;
    Button btnGantiGambar,btnShare;
    ImageView backgroundImageView,imgCoba;
    View relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_quote);
        textAuthor=findViewById(R.id.textViewAuthor);
        textQuote=findViewById(R.id.textviewQuote);
        surahNo=findViewById(R.id.surahNo);
        ayahNo=findViewById(R.id.ayahNo);
        btnGantiGambar=findViewById(R.id.btnGantiGambar);
        btnShare=findViewById(R.id.btnShare);
        relativeLayout=findViewById(R.id.relPicture);
        backgroundImageView=findViewById(R.id.backgroundImageView);
        imgCoba=findViewById(R.id.imgCoba);
        Intent intent=getIntent();
        textQuote.setText(intent.getStringExtra("quote"));
        textAuthor.setText(intent.getStringExtra("author"));
        surahNo.setText(intent.getStringExtra("surahno"));
        ayahNo.setText(intent.getStringExtra("ayahno"));

        btnGantiGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertLayout();
            }
        });

    }

    private void choseImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Pilih Image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            Uri uri=data.getData();

            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                backgroundImageView.setImageBitmap(bitmap);
                Drawable background = new BitmapDrawable(getResources(),bitmap);
                //relativeLayout.setBackground(background);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void convertLayoutToImage(){
//        relativeLayout.setDrawingCacheEnabled(true);
//        relativeLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        relativeLayout.layout(0,0,relativeLayout.getMeasuredWidth(),relativeLayout.getMeasuredHeight());
//        relativeLayout.buildDrawingCache(true);
        //Bitmap bitmap=Bitmap.createBitmap(relativeLayout.getDrawingCache());
//        Bitmap bitmap=Bitmap.createBitmap(relativeLayout.getWidth(),relativeLayout.getHeight(),Bitmap.Config.ARGB_8888);
//        Canvas c=new Canvas(bitmap);
//        relativeLayout.draw(c);
        //relativeLayout.setDrawingCacheEnabled(false);
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();


        Intent share=new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"RSIPWT"+surahNo.getText().toString());
        values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
        Uri uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        OutputStream outputStream;
        File f=new File(Environment.getExternalStorageDirectory() + File.separator+"RSIPWT"+surahNo.getText().toString());
        try{
           // outputStream=getContentResolver().openOutputStream(uri);
//            f.createNewFile();
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//            FileOutputStream fo=new FileOutputStream(f);
//            fo.write(byteArrayOutputStream.toByteArray());
//            byteArrayOutputStream.close();
           // outputStream.close();
            Bitmap bitmap=Bitmap.createBitmap(relativeLayout.getWidth(),relativeLayout.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas c=new Canvas(bitmap);
            relativeLayout.draw(c);
            //FileOutputStream out=new FileOutputStream()
        }catch (Exception e){
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(f));
        startActivity(Intent.createChooser(share,"Bagikan Gambar"));


    }

    private void convertLayout(){
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        relativeLayout.layout(0,0,relativeLayout.getMeasuredWidth(),relativeLayout.getMeasuredHeight());
        relativeLayout.buildDrawingCache(true);
        Bitmap b= Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false);
        imgCoba.setImageBitmap(b);

    }
}