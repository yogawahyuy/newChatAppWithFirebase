package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.rsip.mobile.R;

import java.security.spec.EncodedKeySpec;
import java.util.Hashtable;

public class KartuBpjsActivity extends AppCompatActivity {
    ImageView imgBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_bpjs);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findView();
    }

    private void findView(){
        imgBarcode=findViewById(R.id.imageView_barcode);
        generateBarcode();
    }
    private void  generateBarcode(){
        try{
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap= new Hashtable<EncodeHintType,ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter=new Code128Writer();
            BitMatrix byteMatrix= codeWriter.encode("http://yogawahyuy.github.io", BarcodeFormat.CODE_128,200,50,hintMap);
            int width=byteMatrix.getWidth();
            int height=byteMatrix.getHeight();
            Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i <width ; i++) {
                for (int j = 0; j <height ; j++) {
                    bitmap.setPixel(i,j,byteMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                }
            }
            imgBarcode.setImageBitmap(bitmap);

        }catch (Exception e){

        }
    }
}
