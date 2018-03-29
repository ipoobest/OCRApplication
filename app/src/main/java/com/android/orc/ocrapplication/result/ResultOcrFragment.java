package com.android.orc.ocrapplication.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.DashBoardAdapter;
import com.android.orc.ocrapplication.adapter.ResultListAdapter;
import com.android.orc.ocrapplication.callback.FragmentListener;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.android.orc.ocrapplication.manager.HttpManager;
import com.android.orc.ocrapplication.manager.MenuListManager;
import com.android.orc.ocrapplication.manager.MenuManager;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public class ResultOcrFragment extends Fragment {

    String requestMenu;
    private RecyclerView recyclerView;
    private ResultListAdapter adapter;
    MenuManager menuManager;



    public static ResultOcrFragment newInstance(String request) {
        ResultOcrFragment fragment = new ResultOcrFragment();
        Bundle args = new Bundle();
        args.putString("stringRequest", request);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMenu = getArguments().getString("stringRequest");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ocr_result, container, false);
        initInstances(rootView);
        return rootView;

    }

    private void initInstances(View rootView) {
        menuManager = new MenuManager();

        //find view by id
        recyclerView = rootView.findViewById(R.id.recycler_view_ocr_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        RecyclerViewClickListener listener = (view, position) -> {

            MenuDao dao = menuManager.getDao().get(position);
            FragmentListener fragmentListener = (FragmentListener) getActivity();
            fragmentListener.onMenuClick(dao);

            Toast.makeText(getContext(), "Position " + position, Toast.LENGTH_SHORT).show();

        };

        adapter = new ResultListAdapter(getContext(), listener);

        recyclerView.setAdapter(adapter);
        callQuery();



    }

//    load data
    private void callQuery() {
        Call<List<MenuDao>> call = HttpManager.getInstance().getService().requestMenu(requestMenu);
        call.enqueue(new Callback<List<MenuDao>>() {
            @Override
            public void onResponse(Call<List<MenuDao>> call, Response<List<MenuDao>> response) {
                if (response.isSuccessful()) {
                    List<MenuDao> dao = response.body();
                    menuManager.setDao(dao);
                    adapter.setDao(dao);
                    adapter.notifyDataSetChanged();
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
            public void onFailure(Call<List<MenuDao>> call, Throwable t) {
                Toast.makeText(getContext(),
                        t.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

}