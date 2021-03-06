package com.android.orc.ocrapplication.manager;


import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.CommentDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public interface ApiService {

    @GET("menu/{limit}")
    Call<List<MenuDao>> loadMenuItem(@Path("limit") int limit);

    @GET("menu/{name}")
    Call<MenuDao> loadComment(@Path("name") String menu);

    @GET("menu/querymenu/{name}")
    Call<List<MenuDao>> requestMenu(@Path("name") String menu);


    @GET("menu/filter-sort/{filter}/{sort}/{limit}")
    Call<List<MenuDao>> sortby(@Path("filter") String filter
            , @Path("sort") String sort
            , @Path("limit") int limit);

    @GET("menu/sort/{sort}/{limit}")
    Call<List<MenuDao>> sortbyRating(@Path("sort") String sort
            , @Path("limit") int limit);


    @PUT("review/add/{nameThai}")
    Call<MenuDao> addComment(@Path("nameThai") String name,
                             @Body CommentDao ratingRequest);

}
