package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rsip.mobile.MainActivity;
import com.rsip.mobile.R;

public class KartuActivity extends AppCompatActivity {
    ImageView backImg,qrcodeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_kartu);
        backImg=findViewById(R.id.backimg);
        qrcodeImg=findViewById(R.id.imageqrcode);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KartuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        QRCodeWriter writer=new QRCodeWriter();
        try{
            //BitMatrix bitMatrix=multiFormatWriter.encode(modal, BarcodeFormat.QR_CODE,200,200);

            BitMatrix bitMatrix=writer.encode("rsipurwokerto.co.id", BarcodeFormat.QR_CODE,500,500);
            //BitMatrix bitMatrix=writer.encode("11123345", BarcodeFormat.CODE_128,500,500);
            int width=bitMatrix.getWidth();
            int height=bitMatrix.getHeight();
            Bitmap bm=Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bm.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrcodeImg.setImageBitmap(bm);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }


}
