package com.example.chatwithfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatwithfirebase.Fragments.AboutFragment;
import com.example.chatwithfirebase.Fragments.ChatsFragment;
import com.example.chatwithfirebase.Fragments.HomeFragment;
import com.example.chatwithfirebase.Fragments.UsersFragment;
import com.example.chatwithfirebase.Model.User;
import com.example.chatwithfirebase.View.StartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView profileImage;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private BottomNavigationViewEx bottomNavigationViewEx;
    MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImage =findViewById(R.id.profile_image);
        username=findViewById(R.id.username);

        username.setText(R.string.rsip);
        profileImage.setImageResource(R.drawable.logorsi);
        profileImage.setMaxHeight(50);
        profileImage.setMaxWidth(50);
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

        final ViewPager viewPager=findViewById(R.id.viewPager);
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
                       username.setText(R.string.rsip);
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
                        username.setText("About RSI");
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
                        username.setText("Chat");
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
                        username.setText("Profile");
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
