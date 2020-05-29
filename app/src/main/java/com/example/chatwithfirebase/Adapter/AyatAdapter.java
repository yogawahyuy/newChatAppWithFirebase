package com.example.chatwithfirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatwithfirebase.Model.AyatModel;
import com.example.chatwithfirebase.R;

import java.util.ArrayList;

public class AyatAdapter extends RecyclerView.Adapter<AyatAdapter.ViewHolder> {

    Context context;
    ArrayList<AyatModel> ayatModels;

    public AyatAdapter(Context context, ArrayList<AyatModel> ayatModels) {
        this.context = context;
        this.ayatModels = ayatModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_item_ayat,parent,false);
        AyatAdapter.ViewHolder mholder=new AyatAdapter.ViewHolder(view);
        return mholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.surahNumber.setText(ayatModels.get(position).getNumberAyat());
        holder.arTranslation.setText(ayatModels.get(position).getTextArab());
        holder.idTranslation.setText(ayatModels.get(position).getIdTranslation());
    }

    @Override
    public int getItemCount() {
        return ayatModels!=null?ayatModels.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView surahNumber,arTranslation,idTranslation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            surahNumber=itemView.findViewById(R.id.tv_nosurah);
            arTranslation=itemView.findViewById(R.id.tv_arab);
            idTranslation=itemView.findViewById(R.id.tv_translation);
        }
    }
}
