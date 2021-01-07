package com.rsip.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.rsip.mobile.Fragments.AboutFragment;
import com.rsip.mobile.Fragments.ChatsFragment;
import com.rsip.mobile.Fragments.HomeFragment;
import com.rsip.mobile.Fragments.UsersFragment;
import com.rsip.mobile.Utils.CacheUtil;
import com.rsip.mobile.View.StartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.rsip.mobile.View.TentangActivity;

import java.util.ArrayList;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;

public class MainActivity extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler {

    ImageView profileImage,akreditasiImage;
    //TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private BottomNavigationViewEx bottomNavigationViewEx;
    MenuItem prevMenuItem;
    InAppUpdateManager inAppUpdateManager;
    private static final int REQ_CODE_VERSION_UPDATE = 530;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        InAppUpdateManager.Builder(this,REQ_CODE_VERSION_UPDATE)
//                .resumeUpdates(true)
//                .mode(Constants.UpdateMode.IMMEDIATE)
//                .checkForAppUpdate();
////        inAppUpdateManager.checkForAppUpdate();
        updateManager();
        CacheUtil cacheUtil=new CacheUtil();
        cacheUtil.deleteCache(this);
//        profileImage=findViewById(R.id.logorsi);
//        profileImage.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                startActivity(new Intent(MainActivity.this, TentangActivity.class));
//                return false;
//            }
//        });
        bottomNavigationViewEx=findViewById(R.id.bottom_nav_view);
        bottomNavigationViewEx.setIconSize(20, 20);
        bottomNavigationViewEx.setTextSize(12);
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.setTextVisibility(true);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorAccent));
        bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorAccent));


        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });

        final ViewPager viewPager=findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new HomeFragment());
        viewPagerAdapter.addFragment(new AboutFragment());
        viewPagerAdapter.addFragment(new ChatsFragment());
        viewPagerAdapter.addFragment(new UsersFragment());
        viewPager.setAdapter(viewPagerAdapter);



        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_menu:
                        viewPager.setCurrentItem(0);
                        bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorAccent));
                       bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorAccent));
                       bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorGREY));
                       bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorGREY));
                       bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorGREY));
                       bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorGREY));
                       bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorGREY));
                       bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorGREY));
                       //username.setText(R.string.rsip);
                        break;
                    case R.id.about_menu:
                        viewPager.setCurrentItem(1);
                        bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorAccent));
                        bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorAccent));
                        bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorGREY));
                       // username.setText("About RSI");
                        break;
                    case R.id.chat_menu:
                        viewPager.setCurrentItem(2);
                        bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorAccent));
                        bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorAccent));
                        bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorGREY));
                        //username.setText("Chat");
                        break;
                    case R.id.profile_menu:
                        viewPager.setCurrentItem(3);
                        bottomNavigationViewEx.setIconTintList(0, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(0, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setIconTintList(1, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(1, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setIconTintList(2, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setTextTintList(2, getResources().getColorStateList(R.color.colorGREY));
                        bottomNavigationViewEx.setIconTintList(3, getResources().getColorStateList(R.color.colorAccent));
                        bottomNavigationViewEx.setTextTintList(3, getResources().getColorStateList(R.color.colorAccent));
                        //username.setText("Profile");
                        break;
                }
                return false;

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem!=null){
                    prevMenuItem.setChecked(false);
                }else{
                    bottomNavigationViewEx.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationViewEx.getMenu().getItem(position).setChecked(true);
                prevMenuItem=bottomNavigationViewEx.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void updateManager(){
        inAppUpdateManager=InAppUpdateManager.Builder(this,REQ_CODE_VERSION_UPDATE)
                .resumeUpdates(true)
                .mode(Constants.UpdateMode.FLEXIBLE)
                .snackBarMessage("Update Berhasil Di Download")
                .snackBarMessage("Restart")
                .handler(this);
        inAppUpdateManager.checkForAppUpdate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
                return true;
            case R.id.login:
                startActivity(new Intent(MainActivity.this,StartActivity.class));
                finish();
                return true;
        }
        return false;
    }


    //in appupdate manager
    @Override
    public void onInAppUpdateError(int code, Throwable error) {

    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments=new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment){
            fragments.add(fragment);

        }



    }
}
