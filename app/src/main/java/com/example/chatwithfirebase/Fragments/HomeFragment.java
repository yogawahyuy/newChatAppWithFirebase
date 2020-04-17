package com.example.chatwithfirebase.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chatwithfirebase.Model.HomeCarousellModel;
import com.example.chatwithfirebase.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    View rootView;
    ShimmerFrameLayout mShimmerViewContainer;
    private ArrayList<Integer> carousellModels = new ArrayList<Integer>();

    CarouselView carouselView;
    public HomeFragment() {
        // Required empty public constructor
    }

    ViewListener viewListener=new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            View customView=getActivity().getLayoutInflater().inflate(R.layout.home_view_custom,null);
            ImageView imageView=customView.findViewById(R.id.myImage_home);
            Picasso.get().load(carousellModels.get(position)).into(imageView);
            return customView;
        }
    };

//    ViewListener viewListenerNews = new ViewListener() {
//        @Override
//        public View setViewForPosition(int position) {
//            View customView = getActivity().getLayoutInflater().inflate(R.layout.h,null);
//            return null;
//        }
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mShimmerViewContainer = (ShimmerFrameLayout) rootView.findViewById(R.id.shimer_view_container);
        carouselView = rootView.findViewById(R.id.slider);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        dataSliderHome(viewListener,carouselView,mShimmerViewContainer);
    }

    private void dataSliderHome(ViewListener viewListener, CarouselView carouselView, ShimmerFrameLayout shimmerFrameLayout){
        carousellModels.add(R.drawable.halapandepan);
        carousellModels.add(R.drawable.bmw);
        carousellModels.add(R.drawable.bmw1);
        carouselView.setViewListener(viewListener);
        carouselView.setPageCount(3);
        shimmerFrameLayout.stopShimmerAnimation();
    }
}
