package com.android.orc.ocrapplication.manager;


import android.view.Menu;

import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public interface ApiService {

    @GET("menu")
    Call<List<MenuItemDao>> loadMenuItem();

    @GET("menu/querymenu/{name}")
    Call<List<MenuDao>> requestMenu(@Path("name") String menu);

}
