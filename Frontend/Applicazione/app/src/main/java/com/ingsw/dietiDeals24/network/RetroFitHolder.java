package com.ingsw.dietiDeals24.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface RetroFitHolder {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://44.203.88.254:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
