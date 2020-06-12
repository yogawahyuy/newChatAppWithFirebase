package com.rsip.mobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsip.mobile.Model.JadwalSolatModel;
import com.rsip.mobile.R;

import java.util.ArrayList;

public class JadwalSholatAdapter extends RecyclerView.Adapter<JadwalSholatAdapter.ViewHolder> {

    Context context;
    ArrayList<JadwalSolatModel> jadwalSolatModels;

    public JadwalSholatAdapter(Context context, ArrayList<JadwalSolatModel> jadwalSolatModels) {
        this.context = context;
        this.jadwalSolatModels = jadwalSolatModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_item_jadwal_solat,parent,false);
        JadwalSholatAdapter.ViewHolder mholder=new JadwalSholatAdapter.ViewHolder(view);
        return mholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.jenis.setText(jadwalSolatModels.get(position).getAzhar());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView jenis,waktu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jenis=itemView.findViewById(R.id.jenis_waktu);
            waktu=itemView.findViewById(R.id.jadwal_jam);

        }
    }
}
