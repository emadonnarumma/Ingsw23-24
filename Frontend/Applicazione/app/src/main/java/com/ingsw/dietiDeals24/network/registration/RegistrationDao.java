package com.ingsw.dietiDeals24.network.registration;

import com.ingsw.dietiDeals24.network.TokenHolder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationDao {
    @POST("auth/register")
    public Call<TokenHolder> register(@Body RegistrationRequest registrationRequest);
}
