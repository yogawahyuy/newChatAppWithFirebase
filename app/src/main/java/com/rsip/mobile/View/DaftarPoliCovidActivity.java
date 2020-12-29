package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rsip.mobile.R;

public class DaftarPoliCovidActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_poli_covid);
        progresDialog();
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);


        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdJQZQA5auz9g4sg4niLMyReDidR5s9MozGrp1j0X3GTJMi2g/viewform?usp=sf_link");
        //webView.loadUrl("https://forms.gle/nv1LWTVsryPj1c4x7");

    }

    private void progresDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Data");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
    }



}