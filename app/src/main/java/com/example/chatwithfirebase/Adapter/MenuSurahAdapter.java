package com.example.chatwithfirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatwithfirebase.Model.MenuSurahModel;
import com.example.chatwithfirebase.R;

import java.util.ArrayList;

public class MenuSurahAdapter extends RecyclerView.Adapter<MenuSurahAdapter.ViewHolder> {
    Context context;
    ArrayList<MenuSurahModel> surahModels;

    public MenuSurahAdapter(Context context, ArrayList<MenuSurahModel> surahModels) {
        this.context = context;
        this.surahModels = surahModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_item_surah,parent,false);
        MenuSurahAdapter.ViewHolder mHolder=new MenuSurahAdapter.ViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
