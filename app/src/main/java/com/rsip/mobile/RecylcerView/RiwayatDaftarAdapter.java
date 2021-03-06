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
public class RiwayatDaftarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<RiwayatDaftarModel> modelList;

    private OnItemClickListener mItemClickListener;


    public RiwayatDaftarAdapter(Context context, ArrayList<RiwayatDaftarModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<RiwayatDaftarModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.riwayatdaftar_recycler_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final RiwayatDaftarModel model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.noAntrian.setText(model.getNomorantrean());
            genericViewHolder.namaPasien.setText(model.getNamapasien());
            genericViewHolder.namaPoli.setText(model.getNamapoli());
            genericViewHolder.jamPelayanan.setText(model.getJamdilayani());


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private RiwayatDaftarModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, RiwayatDaftarModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView noAntrian;
        private TextView namaPasien;
        private TextView namaPoli;
        private TextView jamPelayanan;


        public ViewHolder(final View itemView) {
            super(itemView);

            this.noAntrian = (TextView) itemView.findViewById(R.id.textView_noantrians);
            this.namaPasien = (TextView) itemView.findViewById(R.id.textView_namapasien);
            this.namaPoli = (TextView) itemView.findViewById(R.id.textView_namaPoli);
            this.jamPelayanan = (TextView) itemView.findViewById(R.id.textView_jampelayanan);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

