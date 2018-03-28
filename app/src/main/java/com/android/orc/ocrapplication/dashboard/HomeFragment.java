package com.android.orc.ocrapplication.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.DashBoardAdapter;
import com.android.orc.ocrapplication.callback.FragmentListener;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;
import com.android.orc.ocrapplication.description.DescriptionActivity;
import com.android.orc.ocrapplication.login.LoginActivity;
import com.android.orc.ocrapplication.manager.HttpManager;
import com.android.orc.ocrapplication.manager.MenuListManager;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by j.poobest on 9/24/2017 AD.
 */

public class HomeFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private DashBoardAdapter adapter;
    MenuListManager menuListManager;

    FloatingSearchView mSearchView;
    DrawerLayout mDrawer;
    NavigationView navigationView;

    public HomeFragment() {
        super();
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_dashboard, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        menuListManager = new MenuListManager();


        // set floatingView
        mDrawer = rootView.findViewById(R.id.drawer_layout);
        mSearchView = rootView.findViewById(R.id.floating_search_view);
        navigationView = rootView.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = rootView.findViewById(R.id.recycler_view_dashboard);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        RecyclerViewClickListener listener = (view, position) -> {

            MenuItemDao dao = menuListManager.getDao().get(position);
            FragmentListener fragmentListener = (FragmentListener) getActivity();
            fragmentListener.onMenuItemClick(dao);

            Toast.makeText(getContext(), "Position " + position, Toast.LENGTH_SHORT).show();

        };

        adapter = new DashBoardAdapter(getContext(), listener);
        recyclerView.setAdapter(adapter);

        loadData();

    }

    public void loadData() {
        Call<List<MenuItemDao>> call = HttpManager.getInstance().getService().loadMenuItem();
        call.enqueue(new Callback<List<MenuItemDao>>() {
            @Override
            public void onResponse(Call<List<MenuItemDao>> call, Response<List<MenuItemDao>> response) {

                if (response.isSuccessful()) {
                    List<MenuItemDao> dao = response.body();
                    Log.d("Hello", dao.get(0).getDescription());
                    //ดึง dao
                    menuListManager.setDao(dao);
                    adapter.setDao(dao);
                    adapter.notifyDataSetChanged();
//                    Toast.makeText(getContext(),
//                            dao.get(5).getImgUrl(),
//                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Toast.makeText(getContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MenuItemDao>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);
    }




    private void setupSearchBar() {

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String result) {
                if (result != null) {
//                    Toast.makeText(getContext(), ""+result, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), DescriptionActivity.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "please", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }


    private void attachSearchViewActivityDrawer(FloatingSearchView mSearchView) {
        mSearchView.attachNavigationDrawerToMenuButton(mDrawer);
    }

    private void setupDrawerLayout() {
        attachSearchViewActivityDrawer(mSearchView);
    }


    private void goLoginScreen() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mDrawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.logout_facebook) {
            // Handle the camera action
        }
        return true;

    }
}
