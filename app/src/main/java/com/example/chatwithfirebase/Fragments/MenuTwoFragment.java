package com.example.chatwithfirebase.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.chatwithfirebase.Adapter.GridHomeAdapter;
import com.example.chatwithfirebase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuTwoFragment extends Fragment {
    String[] title={"Menu1","Menu2","Menu 3","Menu 3","Menu 3","Menu 3"};
    int[] image={R.drawable.icons8newticket96,R.drawable.icons8orderhistory80,R.drawable.icons8hospitalbed80,R.drawable.icons8doctormale96,R.drawable.calendar,R.drawable.icons8info80};
    View rootView;
    GridView gridView;
    public MenuTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_menu_two, container, false);
        gridView=rootView.findViewById(R.id.gridview);
        GridHomeAdapter gridHomeAdapter=new GridHomeAdapter(getContext(),title,image);
        gridView.setAdapter(gridHomeAdapter);
        return rootView;
    }
}
