package com.android.orc.ocrapplication.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.orc.ocrapplication.activity.LoginActivity;
import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dashboard.fragment.CameraFragment;
import com.android.orc.ocrapplication.dashboard.fragment.GalleryFragment;
import com.android.orc.ocrapplication.dashboard.fragment.HomeFragment;
import com.facebook.login.LoginManager;



public class DashBoardActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{


    BottomNavigationView bottomNavigationView;
    Button logout_facebook;

    //This is our viewPager
    private ViewPager viewPager;


    //Fragments
    CameraFragment cameraFragment;
    GalleryFragment galleryFragment;
    HomeFragment homeFragment;

    MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initInstance();

    }

    private void initInstance() {

        //Login Facebook
//        if (AccessToken.getCurrentAccessToken() == null) {
//            goLoginScreen();
//        }

        logout_facebook = findViewById(R.id.logout_facebook);

        //Initializing viewPager
        viewPager = findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_camera:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_home:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_gallery:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(this);
        setupViewPager(viewPager);

        bottomNavigationView.setSelectedItemId(R.id.action_home);

        logout_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == logout_facebook){
                    LoginManager.getInstance().logOut();
                    goLoginScreen();
                }
            }
        });

    }

    private void goLoginScreen() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        cameraFragment = new CameraFragment();
        homeFragment = new HomeFragment();
        galleryFragment = new GalleryFragment();
        adapter.addFragment(cameraFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(galleryFragment);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }

        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigationView.getMenu().getItem(position);


    }

    @Override
    public void onPageScrollStateChanged(int state) {

//       viewPager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });
//

    }
}
