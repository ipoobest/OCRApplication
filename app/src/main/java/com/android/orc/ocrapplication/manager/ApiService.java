package com.android.orc.ocrapplication.manager;


import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.android.orc.ocrapplication.dao.Rating;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public interface ApiService {

    @GET("menu")
    Call<List<MenuItemDao>> loadMenuItem();

    @GET("/menu/querymenu/{name}")
    Call<List<MenuDao>> requestMenu(@Path("name") String menu);

    @PUT("/review/add/{namethai}")
    Call<List<Rating>> addComment(@Path("namethai") RequestBody requestBody);

}
