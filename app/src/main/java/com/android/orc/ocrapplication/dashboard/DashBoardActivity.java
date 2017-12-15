package com.android.orc.ocrapplication.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.activity.LoginActivity;
import com.android.orc.ocrapplication.camera.CameraActivity;
import com.android.orc.ocrapplication.dashboard.fragment.FavoriteFragment;
import com.android.orc.ocrapplication.dashboard.fragment.HomeFragment;


public class DashBoardActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener {


    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingCameraButton;
//    Button logout_facebook;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
    FavoriteFragment favoriteFragment;
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

//        logout_facebook = findViewById(R.id.logout_facebook);

        viewPager = findViewById(R.id.viewpager);
        floatingCameraButton = findViewById(R.id.dashboard_camera);
        floatingCameraButton.setOnClickListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_favorite:
                                viewPager.setCurrentItem(1);
                                break;
//                            case R.id.action_gallery:
////                                viewPager.setCurrentItem(2);
//                                Intent intent = new Intent(DashBoardActivity.this,
//                                        CameraActivity.class);
//                                startActivity(intent);
//
//                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(this);
        setupViewPager(viewPager);

        bottomNavigationView.setSelectedItemId(R.id.action_home);

//        logout_facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v == logout_facebook){
//                    LoginManager.getInstance().logOut();
//                    goLoginScreen();
//                }
//            }
//        });

    }

    private void goLoginScreen() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        favoriteFragment = new FavoriteFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(favoriteFragment);
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

    @Override
    public void onClick(View v) {
        if (v == floatingCameraButton){
            Intent intent = new Intent(DashBoardActivity.this,
                    CameraActivity.class);
            startActivity(intent);
        }
    }
}
