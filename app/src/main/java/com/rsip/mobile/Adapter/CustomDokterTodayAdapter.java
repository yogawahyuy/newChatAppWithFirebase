package com.rsip.mobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;
import com.rsip.mobile.Model.CustomDokterTodayModel;
import com.rsip.mobile.R;
import com.rsip.mobile.RecylcerView.DokterTodayAdapter;
import com.rsip.mobile.RecylcerView.DokterTodayModel;

import java.util.ArrayList;

public class CustomDokterTodayAdapter extends RecyclerView.Adapter<CustomDokterTodayAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<CustomDokterTodayModel> modelList;
    StorageReference storageReference;
    private OnItemClickListener mItemClickListener;

    public CustomDokterTodayAdapter(Context mContext, ArrayList<CustomDokterTodayModel> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_doktertoday_recycler_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            final CustomDokterTodayModel model= getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            genericViewHolder.itemTxtTitle.setText(model.getNamaDokter());
            genericViewHolder.itemTxtMessage.setText(model.getSpesialistik());
            // storageReference= FirebaseStorage.getInstance().getReference("ProfilePicture/"+model.getKey()+".jpg");
            //GlideApp.with(mContext).load(storageReference).into(genericViewHolder.imgUser);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position, CustomDokterTodayModel model);
    }
    private CustomDokterTodayModel getItem(int position) {
        return modelList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgUser;
        private TextView itemTxtTitle;
        private TextView itemTxtMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgUser = (ImageView) itemView.findViewById(R.id.img_user);
            this.itemTxtTitle = (TextView) itemView.findViewById(R.id.item_txt_title);
            this.itemTxtMessage = (TextView) itemView.findViewById(R.id.item_txt_message);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });
        }
    }
}
