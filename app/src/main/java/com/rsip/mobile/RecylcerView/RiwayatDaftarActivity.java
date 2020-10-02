package com.rsip.mobile.RecylcerView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

import com.rsip.mobile.R;

import android.widget.Toast;
import android.os.Handler;


public class RiwayatDaftarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    private RiwayatDaftarAdapter mAdapter;

    private ArrayList<RiwayatDaftarModel> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_daftar);

        findViews();
        setAdapter();


    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }


    private void setAdapter() {


        modelList.add(new RiwayatDaftarModel("Android", "Hello " + " Android"));
        modelList.add(new RiwayatDaftarModel("Beta", "Hello " + " Beta"));
        modelList.add(new RiwayatDaftarModel("Cupcake", "Hello " + " Cupcake"));
        modelList.add(new RiwayatDaftarModel("Donut", "Hello " + " Donut"));
        modelList.add(new RiwayatDaftarModel("Eclair", "Hello " + " Eclair"));
        modelList.add(new RiwayatDaftarModel("Froyo", "Hello " + " Froyo"));
        modelList.add(new RiwayatDaftarModel("Gingerbread", "Hello " + " Gingerbread"));
        modelList.add(new RiwayatDaftarModel("Honeycomb", "Hello " + " Honeycomb"));
        modelList.add(new RiwayatDaftarModel("Ice Cream Sandwich", "Hello " + " Ice Cream Sandwich"));
        modelList.add(new RiwayatDaftarModel("Jelly Bean", "Hello " + " Jelly Bean"));
        modelList.add(new RiwayatDaftarModel("KitKat", "Hello " + " KitKat"));
        modelList.add(new RiwayatDaftarModel("Lollipop", "Hello " + " Lollipop"));
        modelList.add(new RiwayatDaftarModel("Marshmallow", "Hello " + " Marshmallow"));
        modelList.add(new RiwayatDaftarModel("Nougat", "Hello " + " Nougat"));
        modelList.add(new RiwayatDaftarModel("Android O", "Hello " + " Android O"));


        mAdapter = new RiwayatDaftarAdapter(RiwayatDaftarActivity.this, modelList);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new RiwayatDaftarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RiwayatDaftarModel model) {

                //handle item click events here
                Toast.makeText(RiwayatDaftarActivity.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });


    }


}
