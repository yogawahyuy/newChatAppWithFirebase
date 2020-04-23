package com.example.chatwithfirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatwithfirebase.Model.InfoBedModel;
import com.example.chatwithfirebase.R;

import java.util.ArrayList;

public class InfoBedAdapter extends RecyclerView.Adapter<InfoBedAdapter.ViewHolder> {

    Context mContext;
    ArrayList<InfoBedModel> infoBedModels;

    public InfoBedAdapter(Context mContext, ArrayList<InfoBedModel> infoBedModels) {
        this.mContext = mContext;
        this.infoBedModels = infoBedModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_bed,parent,false);
        final InfoBedAdapter.ViewHolder mHolder=new InfoBedAdapter.ViewHolder(view);

        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int sisabed= Integer.valueOf(infoBedModels.get(position).getKapasitas()) - Integer.valueOf(infoBedModels.get(position).getTersedia());
        holder.nmKelas.setText(infoBedModels.get(position).getNamaRuang());
        holder.sisaBed.setText(infoBedModels.get(position).getTersedia());
        holder.kapasitasBed.setText(infoBedModels.get(position).getKapasitas());
        holder.tersedia.setText(String.valueOf(sisabed));
        holder.lastUpdate.setText(infoBedModels.get(position).getKodeKelas());
    }

    @Override
    public int getItemCount() {
        return (infoBedModels!=null)?infoBedModels.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nmKelas,sisaBed,kapasitasBed,tersedia,lastUpdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nmKelas=itemView.findViewById(R.id.textView_namakelas);
            sisaBed=itemView.findViewById(R.id.textView_sisabed);
            kapasitasBed=itemView.findViewById(R.id.textView_totalkamar);
            tersedia=itemView.findViewById(R.id.textView_sisatersedia);
            lastUpdate=itemView.findViewById(R.id.textView_lastupdate);
        }
    }
}
