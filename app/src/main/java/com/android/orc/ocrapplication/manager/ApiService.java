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

    @GET("menu/filter-sort/{filter}/{sort}/{limit}")
    Call<List<MenuDao>> loadMenuItem(@Path("filter") String filter,
                                     @Path("sort") String sort,
                                     @Path("limit") int limit);
//    app.get('/menu/filer-sort/:filter/:sort/:limit'

    @GET("menu/querymenu/{name}")
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

    @PUT("review/add/{nameThai}")
    Call<MenuDao> addComment(@Path("nameThai") String name,
                            @Body CommentDao ratingRequest);

}
