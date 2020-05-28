package com.example.chatwithfirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatwithfirebase.Model.MenuSurahModel;
import com.example.chatwithfirebase.R;
import com.example.chatwithfirebase.View.AyatActivity;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.surahNumber.setText(surahModels.get(position).getNumberSurah());
        holder.nameSurah.setText(surahModels.get(position).getNameSurah());
        holder.typeSurah.setText(surahModels.get(position).getTypeSurah());
        holder.numberAyah.setText(surahModels.get(position).getNumberAyah());
        holder.arTranslation.setText(surahModels.get(position).getArTranslation());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClickkMenuSurah", "onClick: "+(position+1));
                Intent intent=new Intent(context, AyatActivity.class);
                intent.putExtra("idposisi",position+1);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return surahModels!=null?surahModels.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView surahNumber,nameSurah,typeSurah,numberAyah,arTranslation;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            surahNumber=itemView.findViewById(R.id.tv_nosurah);
            nameSurah=itemView.findViewById(R.id.tv_namasurah);
            typeSurah=itemView.findViewById(R.id.tv_typesurah);
            numberAyah=itemView.findViewById(R.id.tv_jumlahayat);
            arTranslation=itemView.findViewById(R.id.tv_arab);
            relativeLayout=itemView.findViewById(R.id.rel_item_surah);
        }
    }
}
