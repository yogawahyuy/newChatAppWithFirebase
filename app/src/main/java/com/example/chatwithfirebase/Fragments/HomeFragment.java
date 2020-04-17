package com.example.chatwithfirebase.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatwithfirebase.Model.HomeCarousellModel;
import com.example.chatwithfirebase.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 * TO DO
 *
 * menu untuk dibawah slider home
 * 1.Icon Booking Antrian
 * 2.Riwayat Periksa RSI
 * 3.Tempat Tidur Pasien
 * 4.dokter cuti
 * 5.agenda rsip
 * 6.info dokter
 *
 * menu dibawah icon
 * 1.news
 *
 */
public class HomeFragment extends Fragment {
    View rootView;
    ShimmerFrameLayout mShimmerViewContainer,mShimerViewNews;
    private ArrayList<Integer> carousellModels = new ArrayList<Integer>();
    private ArrayList<HomeCarousellModel> homeCarousellModels = new ArrayList<>();
    private ArrayList<Integer> imageNews =new ArrayList<>();

    CarouselView carouselView,carouselViewNews;
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

    ViewListener viewListenerNews = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customView = getActivity().getLayoutInflater().inflate(R.layout.home_view_news,null);
            TextView headline=customView.findViewById(R.id.myText_Headline);
            TextView desc=customView.findViewById(R.id.myText_news);
            headline.setText(homeCarousellModels.get(position).getHeadline());
            desc.setText(homeCarousellModels.get(position).getDesc());
            return customView;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mShimmerViewContainer = (ShimmerFrameLayout) rootView.findViewById(R.id.shimer_view_container);
        mShimerViewNews=rootView.findViewById(R.id.shimer_view_container_news);
        carouselViewNews=rootView.findViewById(R.id.slider_news);
        carouselView = rootView.findViewById(R.id.slider);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();mShimerViewNews.startShimmerAnimation();
        dataSliderHome(viewListener,carouselView,mShimmerViewContainer);
        datasliderNews(viewListenerNews,carouselViewNews,mShimerViewNews);
    }

    private void dataSliderHome(ViewListener viewListener, CarouselView carouselView, ShimmerFrameLayout shimmerFrameLayout){
        carousellModels.add(R.drawable.halapandepan);
        carousellModels.add(R.drawable.bmw);
        carousellModels.add(R.drawable.bmw1);
        carouselView.setViewListener(viewListener);
        carouselView.setPageCount(3);
        shimmerFrameLayout.stopShimmerAnimation();
    }
    private void datasliderNews(ViewListener viewListener,CarouselView carouselView,ShimmerFrameLayout shimmerFrameLayout){
        homeCarousellModels.add(new HomeCarousellModel("disini ada headlen berita","disini juga ada desc"));
        homeCarousellModels.add(new HomeCarousellModel("disini ada headlen berita2","disini juga ada desc2"));
        homeCarousellModels.add(new HomeCarousellModel("disini ada headlen berita3","disini juga ada desc3"));
        carouselView.setViewListener(viewListener);
        carouselView.setPageCount(homeCarousellModels.size());
        shimmerFrameLayout.stopShimmerAnimation();
    }
}
