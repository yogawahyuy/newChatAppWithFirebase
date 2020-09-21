package com.rsip.mobile.RecylcerView;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.rsip.mobile.R;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class JadwalOperasiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<JadwalOperasiModel> modelList;

    private OnItemClickListener mItemClickListener;


    public JadwalOperasiAdapter(Context context, ArrayList<JadwalOperasiModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<JadwalOperasiModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.jadwal_operasi_recycler_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final JadwalOperasiModel model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.kamar.setText(model.getKamar());
            genericViewHolder.noRegis.setText(model.getNoRegis());
            genericViewHolder.tanggalOperasi.setText(model.getTanggalOperasi());
            genericViewHolder.jamOperasi.setText(model.getJamOperasi());
            genericViewHolder.operator.setText(model.getOperator());
            genericViewHolder.lastupdate.setText("Terakhir Update "+model.getLastupdate());


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private JadwalOperasiModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, JadwalOperasiModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       private TextView noRegis,kamar,tanggalOperasi,jamOperasi,icd10,icd9,operator,spesialistik,lastupdate;

        public ViewHolder(final View itemView) {
            super(itemView);

            noRegis=itemView.findViewById(R.id.textView_no_registrasi);
            kamar=itemView.findViewById(R.id.textView_kamar_operasi);
            tanggalOperasi=itemView.findViewById(R.id.textView_tanggal_Operasi);
            jamOperasi=itemView.findViewById(R.id.textView_jam_Operasi);
            operator=itemView.findViewById(R.id.textView_operator);
            noRegis=itemView.findViewById(R.id.textView_no_registrasi);
            lastupdate=itemView.findViewById(R.id.textView_update);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

