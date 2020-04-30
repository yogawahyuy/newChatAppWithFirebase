package com.example.chatwithfirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatwithfirebase.R;
import com.example.chatwithfirebase.View.AgendaRsiActivity;
import com.example.chatwithfirebase.View.BookingActivity;
import com.example.chatwithfirebase.View.DokterCutiActivity;
import com.example.chatwithfirebase.View.InfoBedActivity;
import com.example.chatwithfirebase.View.InfoDokterActivity;
import com.example.chatwithfirebase.View.RiwayatPeriksaActivity;

public class GridHomeAdapter extends BaseAdapter {

    Context mContext;
    private final String[] title;
    private final int[] image;
    LayoutInflater layoutInflater;

    public GridHomeAdapter(Context mContext, String[] title, int[] image) {
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
        Holder holder=new Holder();
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
                    mContext.startActivity(new Intent(mContext, BookingActivity.class));
                }
                else if (position==1){
                    mContext.startActivity(new Intent(mContext, RiwayatPeriksaActivity.class));

                }else if (position==2){
                    mContext.startActivity(new Intent(mContext, InfoBedActivity.class));
                }
                else if (position==3){
                    mContext.startActivity(new Intent(mContext, DokterCutiActivity.class));
                }
                else if (position==4){
                    mContext.startActivity(new Intent(mContext, AgendaRsiActivity.class));
                }else if (position==5){
                    mContext.startActivity(new Intent(mContext, InfoDokterActivity.class));
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
