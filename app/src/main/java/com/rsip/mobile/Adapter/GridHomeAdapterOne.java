package com.rsip.mobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsip.mobile.R;
import com.rsip.mobile.View.InfoBedActivity;
import com.rsip.mobile.View.JadwalSholatActivity;
import com.rsip.mobile.View.MenuSurahActivity;

public class GridHomeAdapterOne extends BaseAdapter {
    Context mContext;
    private final String[] title;
    private final int[] image;
    LayoutInflater layoutInflater;

    public GridHomeAdapterOne(Context mContext, String[] title, int[] image) {
        this.mContext = mContext;
        this.title = title;
        this.image = image;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        GridHomeAdapterOne.Holder holder=new GridHomeAdapterOne.Holder();
        final View rowView;

        rowView = layoutInflater.inflate(R.layout.homeview_menu,null);
        holder.tv=rowView.findViewById(R.id.grid_text);
        holder.imageView=rowView.findViewById(R.id.grid_image);

        holder.tv.setText(title[position]);
        holder.imageView.setImageResource(image[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==0){
                    mContext.startActivity(new Intent(mContext, MenuSurahActivity.class));
                }
                else if (position==1){
                    mContext.startActivity(new Intent(mContext, JadwalSholatActivity.class));
                }else if (position==2){
                    mContext.startActivity(new Intent(mContext, InfoBedActivity.class));
                }
                else if (position==3){

                }
                else if (position==4){

                }
            }
        });

        return rowView;
    }
    public class Holder{
        TextView tv;
        ImageView imageView;
    }
}
