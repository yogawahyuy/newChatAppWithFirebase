package com.rsip.mobile.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsip.mobile.R;
import com.rsip.mobile.Utils.JsonUtil;

public class QuoteActivity extends AppCompatActivity {

    ImageView bmImage;
    ConstraintLayout view;
    TextView quoteText,quoteAuthor;
    JsonUtil jsonUtil=new JsonUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        quoteAuthor=findViewById(R.id.textViewAuthor);
        quoteText=findViewById(R.id.textKataMutiara);

        //jsonUtil.getQuotes(this,quoteText,quoteAuthor);
        //jsonUtil.getQuotesIslamic(this,quoteText,quoteAuthor);
    }
}
