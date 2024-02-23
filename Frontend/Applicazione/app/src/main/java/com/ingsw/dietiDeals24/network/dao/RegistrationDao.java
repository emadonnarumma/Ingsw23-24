package com.ingsw.dietiDeals24.network.dao;

import com.ingsw.dietiDeals24.model.RegistrationRequest;
import com.ingsw.dietiDeals24.network.TokenHolder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RegistrationDao {
    @POST("auth/register")
    public Call<TokenHolder> register(@Body RegistrationRequest registrationRequest);

    @GET("user/check-email/{email}")
    public Call<Boolean> emailAlreadyExists(@Path("email") String email);
}
