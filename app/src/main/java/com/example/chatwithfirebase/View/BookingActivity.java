package com.example.chatwithfirebase.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chatwithfirebase.R;

public class BookingActivity extends AppCompatActivity {
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        username=findViewById(R.id.username);

        username.setText("Booking");
    }
}