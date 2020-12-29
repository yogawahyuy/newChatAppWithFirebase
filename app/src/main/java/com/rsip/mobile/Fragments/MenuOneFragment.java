package com.rsip.mobile.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.rsip.mobile.Adapter.GridHomeAdapter;
import com.rsip.mobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuOneFragment extends Fragment {
    GridView gridView;
    View rootView;
    String[] title={"Pendaftaran Online","Riwayat Periksa","Tempat Tidur","Dokter Hari Ini","Pendaftaran Poli Covid","Jadwal Dokter"};
    int[] image={R.drawable.icons8newticket96,R.drawable.icons8orderhistory80,R.drawable.icons8hospitalbed80,R.drawable.icons8doctormale96,R.drawable.covid,R.drawable.icons8info80};


    public MenuOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_menu_one, container, false);
        gridView=rootView.findViewById(R.id.gridview);
        GridHomeAdapter gridHomeAdapter=new GridHomeAdapter(getContext(),title,image);
        gridView.setAdapter(gridHomeAdapter);
        return rootView;
    }
}
