package com.android.orc.ocrapplication.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.android.orc.ocrapplication.manager.HttpManager;
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

    ImageView imgMenu;
    TextView tvNameMenu;
    TextView tvDescription;
    TextView tvIngredient;

    String requestMenu;

    public static ResultOcrFragment newInstance(String requset) {
        ResultOcrFragment fragment = new ResultOcrFragment();
        Bundle args = new Bundle();
        args.putString("stringRequest", requset);
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

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        initInstances(rootView);
        return rootView;

    }

    private void initInstances(View rootView) {
        //find view by id
        imgMenu = rootView.findViewById(R.id.image_menu_description);
        tvNameMenu = rootView.findViewById(R.id.text_name_menu_description);
        tvDescription = rootView.findViewById(R.id.text_description_description);
        tvIngredient = rootView.findViewById(R.id.text_ingredient_menu_description);

        callQuery();

//


    }

    private void callQuery() {
        Call<MenuDao> call = HttpManager.getInstance().getService().requestMenu(requestMenu);
        call.enqueue(new Callback<MenuDao>() {
            @Override
            public void onResponse(Call<MenuDao> call, Response<MenuDao> response) {
                if (response.isSuccessful()) {
                    MenuDao dao = response.body();
                    //ดึง dao
                    tvNameMenu.setText(dao.getName());
                    tvDescription.setText(dao.getDescription());
                    tvIngredient.setText(dao.getIngredient());
                    Glide.with(ResultOcrFragment.this)
                            .load(dao.getImgUrl())
                            .into(imgMenu);
                } else {
                    try {
                        tvIngredient.setText(response.errorBody().string());
                        Toast.makeText(getContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MenuDao> call, Throwable t) {

            }
        });
    }


}