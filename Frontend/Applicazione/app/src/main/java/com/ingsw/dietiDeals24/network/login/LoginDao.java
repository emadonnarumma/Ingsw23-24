package com.ingsw.dietiDeals24.network.login;

import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.network.TokenHolder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginDao {
    @POST("auth/authenticate")
    Call<TokenHolder> login(@Body LogInRequest logInRequest);
}
