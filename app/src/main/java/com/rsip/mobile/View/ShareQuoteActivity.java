package com.rsip.mobile.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rsip.mobile.BuildConfig;
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
    RelativeLayout relativeLayout;
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
        //imgCoba=findViewById(R.id.imgCoba);
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
                //convertLayout();
                if (isStoragePermissionGranted()){
                   //shareQuote();
                   // saveImageToInternalExternal();
                    shareImage();
                }
            }
        });

        textAuthor.measure(0,0);
        relativeLayout.measure(0,0);
        int height=textAuthor.getMeasuredHeight();
        int width=textAuthor.getMeasuredWidth();
        int heightRel=relativeLayout.getMeasuredHeight();
        int widtthRel=relativeLayout.getMeasuredWidth();
//        Log.d("tinggi", "onCreate: "+height);
//        Log.d("tinggi", "onCreate: "+width);
//        Log.d("tinggiRel", "onCreate: "+heightRel);
//        Log.d("tinggiRel", "onCreate: "+widtthRel);
//        Log.d("display", "onCreate: "+getScreenHeight(this));

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

    private boolean isStoragePermissionGranted(){
        if (Build.VERSION.SDK_INT >= 24){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                Log.d("Permission", "isStoragePermissionGranted: Diijinkan");
                return true;
            }else {
                Log.d("Permission", "isStoragePermissionGranted: Tidak Diijinkan");
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                return false;
            }
        }else{
            Log.d("Permission", "isStoragePermissionGranted: Diijinkans");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 2:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(this, "Permission Diijinkan", Toast.LENGTH_SHORT).show();
                    //convertLayout();
                    //shareQuote();
                    //saveImageToInternalExternal();
                    shareImage();
                }else {
                    //Toast.makeText(this, "Permission Ditolak", Toast.LENGTH_SHORT).show();
                }
                return;
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
        relativeLayout.layout(0,0,relativeLayout.getWidth(),relativeLayout.getHeight());
        relativeLayout.buildDrawingCache(true);
        Bitmap b= Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false);
        imgCoba.setImageBitmap(b);

        ContextWrapper cw=new ContextWrapper(getApplicationContext());
        File directory=cw.getDir("RSIMOBILE", Context.MODE_PRIVATE);
        File root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir=new File(root.getAbsolutePath()+"/rsimobile");
        //dir.mkdirs();
       // Log.d("convert", "convertLayout: "+dir.toString());
        String fileName="RsiMobile-"+surahNo.getText().toString()+".jpg";
        File file=new File(directory,fileName);
        Log.d("path", "convertLayout: "+file.toString());
        if (!file.exists())
        {
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                b.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                Toast.makeText(this, "Berhasil Menyimpan", Toast.LENGTH_SHORT).show();
                file.setReadable(true,false);

                Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                sendBroadcast(intent);

                Intent intentShare=new Intent(Intent.ACTION_SEND);
                intentShare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentShare.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
                intentShare.setType("image/jpg");
                startActivity(Intent.createChooser(intentShare,"Bagikan Ke"));
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

    private void shareImage(){
       Bitmap bitmap = getBitmapFromView(relativeLayout);
        String fileName="RsiMobile-"+surahNo.getText().toString()+".jpg";
        String filePath=this.getFilesDir().getPath().toString() + "/RsiMobile-"+surahNo.getText().toString()+".jpg";
        File file=new File(filePath);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            file.setReadable(true,false);
            Uri uri= FileProvider.getUriForFile(ShareQuoteActivity.this, BuildConfig.APPLICATION_ID+".provider",file);
            Intent intentShare=new Intent(Intent.ACTION_SEND);
            intentShare.putExtra(Intent.EXTRA_STREAM,uri);
            intentShare.setType("image/jpg");
            startActivity(Intent.createChooser(intentShare,"Bagikan Ke"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveImageToInternalExternal(){
        if (checkIfExternalStorageExist()){
            Bitmap bitmap=getBitmapFromView(relativeLayout);

            File root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File dir=new File(root.getAbsolutePath()+"/rsimobile");
            dir.mkdirs();
            Log.d("convert", "convertLayout: "+dir.toString());
            String fileName="RsiMobile-"+surahNo.getText().toString()+".jpg";
            File file=new File(dir,fileName);

            if (!file.exists()){
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(this, "Berhasil Menyimpan", Toast.LENGTH_SHORT).show();
                    file.setReadable(true,false);

                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(file));
                    sendBroadcast(intent);
                    Intent intentShare=new Intent(Intent.ACTION_SEND);
                    //intentShare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri= FileProvider.getUriForFile(ShareQuoteActivity.this, BuildConfig.APPLICATION_ID+".provider",file);
                    intentShare.putExtra(Intent.EXTRA_STREAM,uri);
                    intentShare.setType("image/jpg");
                    startActivity(Intent.createChooser(intentShare,"Bagikan Ke"));
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("convertgagal", "shareQuote: "+e.getMessage());
                }
            }
        }else{
            Bitmap bitmap=getBitmapFromView(relativeLayout);
            ContextWrapper cw=new ContextWrapper(getApplicationContext());
            File directory=cw.getDir("RSIMOBILE", Context.MODE_PRIVATE);
            String fileName="RsiMobile-"+surahNo.getText().toString()+".jpg";
            File file=new File(directory,fileName);
            if (!file.exists()){
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(this, "Berhasil Menyimpan", Toast.LENGTH_SHORT).show();
                    file.setReadable(true,false);

                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(file));
                    sendBroadcast(intent);
                    Intent intentShare=new Intent(Intent.ACTION_SEND);
                    //intentShare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri= FileProvider.getUriForFile(ShareQuoteActivity.this, BuildConfig.APPLICATION_ID+".provider",file);
                    intentShare.putExtra(Intent.EXTRA_STREAM,uri);
                    intentShare.setType("image/jpg");
                    startActivity(Intent.createChooser(intentShare,"Bagikan Ke"));
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("convertgagal", "shareQuote: "+e.getMessage());
                }
            }
        }
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

    @SuppressLint("SetWorldReadable")
    private void shareQuote(){
        Bitmap bitmap=getBitmapFromView(relativeLayout);

            File root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File dir=new File(root.getAbsolutePath()+"/rsimobile");
            dir.mkdirs();
            Log.d("convert", "convertLayout: "+dir.toString());
            String fileName="RsiMobile-"+surahNo.getText().toString()+".jpg";
            File file=new File(dir,fileName);

            if (!file.exists()){
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(this, "Berhasil Menyimpan", Toast.LENGTH_SHORT).show();
                    file.setReadable(true,false);

                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(file));
                    sendBroadcast(intent);
                    Intent intentShare=new Intent(Intent.ACTION_SEND);
                    //intentShare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri= FileProvider.getUriForFile(ShareQuoteActivity.this, BuildConfig.APPLICATION_ID+".provider",file);
                    intentShare.putExtra(Intent.EXTRA_STREAM,uri);
                    intentShare.setType("image/jpg");
                    startActivity(Intent.createChooser(intentShare,"Bagikan Ke"));
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("convertgagal", "shareQuote: "+e.getMessage());
                }
            }

    }

    private boolean checkIfExternalStorageExist(){
        String state=Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    private static int getScreenHeight(Context context){
        DisplayMetrics dm=new DisplayMetrics();
        WindowManager windowManager=(WindowManager)context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int heightInDp=Math.round(dm.heightPixels/dm.density);
        return heightInDp;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getRelativeInfo();
    }

    private void getRelativeInfo(){
        float height=relativeLayout.getHeight();
        float width=relativeLayout.getWidth();
        float heightText=textAuthor.getHeight();
        float widthText=textAuthor.getWidth();
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.height=2000;
        Log.d("Tinggi", "getRelativeInfo: "+height);
        Log.d("Tinggis", "getRelativeInfo: "+width);
        Log.d("TinggiText", "getRelativeInfo: "+heightText);
        Log.d("TinggiText", "getRelativeInfo: "+widthText);
        if (heightText>500){
//          backgroundImageView.requestLayout();
//          backgroundImageView.getLayoutParams().height=2500;
            relativeLayout.requestLayout();
            relativeLayout.getLayoutParams().height=1500;
        }
    }
}