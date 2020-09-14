package com.rsip.mobile.View;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rsip.mobile.Model.GlideApp;
import com.rsip.mobile.R;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class SemuaDokterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<SemuaDokterModel> modelList;

    private OnItemClickListener mItemClickListener;
    StorageReference storageReference;


    public SemuaDokterAdapter(Context context, ArrayList<SemuaDokterModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<SemuaDokterModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dokter_recycler_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final SemuaDokterModel model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            storageReference= FirebaseStorage.getInstance().getReference("ProfilePicture/"+model.getKey()+".jpg");



            genericViewHolder.itemTxtTitle.setText(model.getNamaDokter());
            genericViewHolder.itemTxtMessage.setText(model.getSpesialistik());

            Log.d("isi storage", "onBindViewHolder: "+storageReference);

            //String url=modelList.get(position).getUrlPhoto();

            //Uri uri= Uri.parse(url);
            //genericViewHolder.imgUser.setImageURI(uri);
            GlideApp.with(mContext).load(storageReference).into(genericViewHolder.imgUser);




        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private SemuaDokterModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, SemuaDokterModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView itemTxtTitle;
        private TextView itemTxtMessage;


        public ViewHolder(final View itemView) {
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

