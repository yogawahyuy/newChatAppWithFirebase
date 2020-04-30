package com.example.chatwithfirebase.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MenuViewAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    Context context;

    public MenuViewAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context=context;
        this.fragments=new ArrayList<>();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }
}
