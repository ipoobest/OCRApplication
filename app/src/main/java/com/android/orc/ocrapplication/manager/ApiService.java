package com.android.orc.ocrapplication.manager;


import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.android.orc.ocrapplication.dao.Rating;
import com.android.orc.ocrapplication.dao.RatingDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public interface ApiService {

    @GET("menu")
    Call<List<MenuItemDao>> loadMenuItem();

    @GET("/menu/querymenu/{name}")
    Call<List<MenuDao>> requestMenu(@Path("name") String menu);
//
//    @PUT("/review/add/{namethai}")
//    Call<List<Rating>> addComment(@Path("namethai") String nameThai,
//                                  @Part("user") String userName,
//                                  @Part("comment") String comment,
//                                  @Part("rating") Double rating);

//    @PUT("/review/add/{namethai}")
//    void addComment(@Path("namethai") String nameThai,
//                    @Part("user") String userName,
//                    @Part("comment") String comment,
//                    @Part("rating") Double rating,
//                    Callback<Rating> serverResponseCallback);

    //TODO: PUT METHODS
    @PUT("/review/add/{nameThai}")
    Call<MenuDao> addComment(@Path("nameThai") String name,
                            @Body RatingDao ratingRequest);

}
