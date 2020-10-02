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
public class HistoriPeriksaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<HistoryPeriksaModel> modelList;

    private OnItemClickListener mItemClickListener;


    public HistoriPeriksaAdapter(Context context, ArrayList<HistoryPeriksaModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<HistoryPeriksaModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_periksa_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final HistoryPeriksaModel model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.nmPoli.setText(model.getPoliTuju());
            genericViewHolder.tanggal.setText(model.getTglPeriksa());
            genericViewHolder.noRm.setText(model.getNoRM());
            genericViewHolder.drTuju.setText(model.getDrTuju());


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private HistoryPeriksaModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, HistoryPeriksaModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nmPoli,tanggal,noRm,drTuju;

        public ViewHolder(final View itemView) {
            super(itemView);

            nmPoli=itemView.findViewById(R.id.textview_nmpPoliHistory);
            tanggal=itemView.findViewById(R.id.textview_tanggalHistori);
            noRm=itemView.findViewById(R.id.textview_normHistori);
            drTuju=itemView.findViewById(R.id.textview_drtujuHistori);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

