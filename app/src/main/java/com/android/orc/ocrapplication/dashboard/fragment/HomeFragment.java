package com.android.orc.ocrapplication.dashboard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.orc.ocrapplication.LoginActivity;
import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.MenuListAdapter;
import com.android.orc.ocrapplication.dashboard.DescriptionActivity;
import com.android.orc.ocrapplication.model.dao.MenuListItem;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by j.poobest on 9/24/2017 AD.
 */

public class HomeFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private List<MenuListItem> listResult;
    private MenuListAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    FloatingSearchView mSearchView;
    DrawerLayout mDrawer;
    NavigationView navigationView;
    CardView cardView;

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

        // set firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("menu");

        // set floatingView
        mDrawer = rootView.findViewById(R.id.drawer_layout);
        mSearchView = rootView.findViewById(R.id.floating_search_view);
        navigationView = rootView.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // set cardView
        cardView = rootView.findViewById(R.id.card_view);

        setupDrawerLayout();
        setupSearchBar();


        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        listResult = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recycler_view_dashboard);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new MenuListAdapter(getContext(), listResult);
        recyclerView.setAdapter(adapter);

        updateList();

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

    private void updateList() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listResult.add(dataSnapshot.getValue(MenuListItem.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                MenuListItem listItem = dataSnapshot.getValue(MenuListItem.class);

                int index = getItemIndex(listItem);

                listResult.set(index, listItem);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MenuListItem listItem = dataSnapshot.getValue(MenuListItem.class);

                int index = getItemIndex(listItem);

                listResult.remove(index);
                adapter.notifyItemRemoved(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getItemIndex(MenuListItem menu) {

        int index = -1;

        for (int i = 0; i < listResult.size(); i++) {
            if (listResult.get(i).key.equals(menu.key)) {
                index = i;
                break;
            }
        }
        return index;
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

    //TODO
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
