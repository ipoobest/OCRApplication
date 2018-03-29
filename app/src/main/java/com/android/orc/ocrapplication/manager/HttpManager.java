package com.android.orc.ocrapplication.manager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public class HttpManager {

//    private static final String BASE_URL = "http://128.199.154.30:3000/";

    private static HttpManager instances;

    public static HttpManager getInstance() {
        if (instances == null)
            instances = new HttpManager();
        return instances;
    }

    private ApiService service;

    private HttpManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://128.199.154.30:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);

    }

    public ApiService getService() {
        return service;
    }
}
