package com.ingsw.dietiDeals24.network.login;

import com.ingsw.dietiDeals24.network.TokenHolder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginDao {
    @POST("auth/authenticate")
    public Call<TokenHolder> login(@Body LogInRequest logInRequest);
}
