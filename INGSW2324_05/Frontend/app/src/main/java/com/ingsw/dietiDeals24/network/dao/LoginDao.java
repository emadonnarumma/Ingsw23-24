package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.LogInRequest;
import com.ingsw.dietiDeals24.network.TokenHolder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LoginDao {
    @POST("auth/authenticate")
    Call<TokenHolder> login(@Body LogInRequest logInRequest);
}
