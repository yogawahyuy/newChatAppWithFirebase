package com.example.chatwithfirebase.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatwithfirebase.Adapter.GridHomeAdapter;
import com.example.chatwithfirebase.Adapter.GridHomeAdapterOne;
import com.example.chatwithfirebase.Adapter.MenuViewAdapter;
import com.example.chatwithfirebase.Model.GridViewModel;
import com.example.chatwithfirebase.Model.HomeCarousellModel;
import com.example.chatwithfirebase.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.PageIndicator;
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
    GridView gridView,gridView1;
    ShimmerFrameLayout mShimmerViewContainer,mShimerViewNews,mShimerViewMenu;
    ViewPager viewPager;
    PageIndicator pageIndicator;
    private ArrayList<Integer> carousellModels = new ArrayList<Integer>();
    private ArrayList<HomeCarousellModel> homeCarousellModels = new ArrayList<>();
    private ArrayList<GridView> imageNews =new ArrayList<>();
    private ArrayList<GridViewModel> gridViewModels=new ArrayList<>();
    MenuViewAdapter menuViewAdapter;

    String[] title={"Booking","Riwayat Periksa","Tempat Tidur","Dokter Cuti","Agenda RSI","Info Dokter"};
    int[] image={R.drawable.icons8newticket96,R.drawable.icons8orderhistory80,R.drawable.icons8hospitalbed80,R.drawable.icons8doctormale96,R.drawable.calendar,R.drawable.icons8info80};

    CarouselView carouselView,carouselViewNews,carouselViewMenu;
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

    ViewListener viewListenerMenu = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customView = getActivity().getLayoutInflater().inflate(R.layout.homeview_menu,null);
            TextView titles=customView.findViewById(R.id.grid_text);
            ImageView imageView=customView.findViewById(R.id.grid_image);
            for (int i = 0; i <titles.length() ; i++) {
                titles.setText(titles.getText().toString());
               Picasso.get().load(image[position]).into(imageView);
            }
           // gridView=rootView.findViewById(R.id.gridview);
            gridView1=rootView.findViewById(R.id.gridview1);
            GridHomeAdapter gridHomeAdapter=new GridHomeAdapter(getContext(),title,image);
            GridHomeAdapterOne gridHomeAdapter1=new GridHomeAdapterOne(getContext(),title,image);
            //gridView.setAdapter(gridHomeAdapter);
            //gridView1.setAdapter(gridHomeAdapter1);


            return customView;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager=rootView.findViewById(R.id.viewpager_menu);
        menuViewAdapter=new MenuViewAdapter(getContext(),getChildFragmentManager());
//        menuViewAdapter.addFragment(new MenuOneFragment());
//        menuViewAdapter.addFragment(new MenuTwoFragment());
        viewPager.setAdapter(menuViewAdapter);
        pageIndicator=rootView.findViewById(R.id.pageindicator);
        pageIndicator.setViewPager(viewPager);

        mShimmerViewContainer = (ShimmerFrameLayout) rootView.findViewById(R.id.shimer_view_container);
        mShimerViewNews=rootView.findViewById(R.id.shimer_view_container_news);
//        mShimerViewMenu=rootView.findViewById(R.id.shimer_view_container_menu);
        carouselViewMenu=rootView.findViewById(R.id.slider_menuicon);
        carouselViewNews=rootView.findViewById(R.id.slider_news);
        carouselView = rootView.findViewById(R.id.slider);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        mShimerViewNews.startShimmerAnimation();
        dataSliderMenu();
//        mShimerViewMenu.startShimmerAnimation();
        //datasliderMenu(viewListenerMenu,carouselViewMenu,mShimerViewMenu);
        dataSliderHome(viewListener,carouselView,mShimmerViewContainer);
        datasliderNews(viewListenerNews,carouselViewNews,mShimerViewNews);
    }

    private void dataSliderHome(ViewListener viewListener, CarouselView carouselView, ShimmerFrameLayout shimmerFrameLayout){
        carousellModels.add(R.drawable.slide3);
        carousellModels.add(R.drawable.slide2);
        carousellModels.add(R.drawable.slide1);
        carouselView.setViewListener(viewListener);
        carouselView.setPageCount(3);
        shimmerFrameLayout.stopShimmerAnimation();
    }
    private void datasliderNews(ViewListener viewListener,CarouselView carouselView,ShimmerFrameLayout shimmerFrameLayout){
        homeCarousellModels.add(new HomeCarousellModel("RSIP Paripurna SNARS 1","Terakreditasi Paripurna SNARS 1 tahun 2019 menjadikan RSI Purwokerto"));
        homeCarousellModels.add(new HomeCarousellModel("Laparoscopy unggulan RSIP","Fasilitas unggulan bedah minimal invasif yang ada di RSI Purwokerto"));
        homeCarousellModels.add(new HomeCarousellModel("Vitreo Retina Layanan Unggulan","Salah satu layanan unggulan di RSI Purwokerto dan salah satu dari 4 sub spesialis mata di Jawa Tengah"));
        carouselView.setViewListener(viewListener);
        carouselView.setPageCount(3);
        shimmerFrameLayout.stopShimmerAnimation();
    }
    private void datasliderMenu(ViewListener viewListener,CarouselView carouselView,ShimmerFrameLayout shimmerFrameLayout){
        imageNews.add(gridView);
        imageNews.add(gridView1);
        carouselView.setViewListener(viewListener);
        carouselView.setPageCount(2);
        shimmerFrameLayout.stopShimmerAnimation();
    }

    private void dataSliderMenu(){
//        MenuViewAdapter menuViewAdapter=new MenuViewAdapter(getContext(),getFragmentManager());
        menuViewAdapter.addFragment(new MenuOneFragment());
        menuViewAdapter.addFragment(new MenuTwoFragment());
//        viewPager.setAdapter(menuViewAdapter);
//        pageIndicator=rootView.findViewById(R.id.pageindicator);
//        pageIndicator.setViewPager(viewPager);
    }
}
