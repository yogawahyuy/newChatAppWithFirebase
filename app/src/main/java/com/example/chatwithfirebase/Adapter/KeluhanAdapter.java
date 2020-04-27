package com.example.chatwithfirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatwithfirebase.Model.KeluhanModel;
import com.example.chatwithfirebase.R;

import java.util.ArrayList;

public class KeluhanAdapter extends RecyclerView.Adapter<KeluhanAdapter.ViewHolder> {

    Context context;
    ArrayList<KeluhanModel> keluhanModels;

    public KeluhanAdapter(Context context, ArrayList<KeluhanModel> keluhanModels) {
        this.context = context;
        this.keluhanModels = keluhanModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.custom_layout_daftar_keluhan,parent,false);
        final KeluhanAdapter.ViewHolder mHolder = new KeluhanAdapter.ViewHolder(view);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nama.setText(keluhanModels.get(position).getNama());
        holder.tgl.setText(keluhanModels.get(position).getTanggal());
        holder.kategori.setText(keluhanModels.get(position).getKategori());
        holder.unit.setText(keluhanModels.get(position).getUnit());
        holder.keluhan.setText(keluhanModels.get(position).getKeluhan());
        holder.balasan.setText(keluhanModels.get(position).getPesanBalasan());
    }

    @Override
    public int getItemCount() {
        return keluhanModels!=null?keluhanModels.size():0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama,tgl,kategori,unit,keluhan,balasan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama=itemView.findViewById(R.id.rec_text_nama);
            tgl=itemView.findViewById(R.id.rec_text_tgl);
            kategori=itemView.findViewById(R.id.rec_text_kategori);
            unit=itemView.findViewById(R.id.rec_text_unit);
            keluhan=itemView.findViewById(R.id.rec_text_keluhan);
            balasan=itemView.findViewById(R.id.rec_text_balasan);
        }
    }
}
