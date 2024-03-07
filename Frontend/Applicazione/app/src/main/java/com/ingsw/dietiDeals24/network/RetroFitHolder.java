package com.ingsw.dietiDeals24.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface RetroFitHolder {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.107.121:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
