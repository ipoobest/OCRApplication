package com.android.orc.ocrapplication.manager;


import com.android.orc.ocrapplication.dao.MenuItemDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public interface ApiService {

    @GET("menu")
    Call<List<MenuItemDao>> loadMenuItem();

    @GET("menu/{nameMenu}")
    Call<List<MenuItemDao>> requestMenu(@Part("nameMenu") String nameMenu);

}
