package com.ingsw.dietiDeals24.authentication;

import com.ingsw.dietiDeals24.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationDao {
    @POST("auth/authenticate")
    public Call<AuthenticationToken> login(@Body LogInRequest logInRequest);


}
