package com.example.vuhung.rxmoviehung.API;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class APIManager {
    public static String BASE_URL = "http://training-movie.bsp.vn:82";
    private Retrofit retrofit = null;

    public static Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
