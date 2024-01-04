package com.ingsw.dietiDeals24.controller;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ingsw.dietiDeals24.authentication.AuthenticationDao;
import com.ingsw.dietiDeals24.authentication.AuthenticationToken;
import com.ingsw.dietiDeals24.authentication.LogInRequest;
import com.ingsw.dietiDeals24.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInController {
    public static boolean isLoggedIn = false;
    public static User loggedUser;

    private LogInController() {
    }

    public static void login(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.199.135:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthenticationDao authenticationDao = retrofit.create(AuthenticationDao.class);
        Call<AuthenticationToken> call = authenticationDao.login(new LogInRequest(email, password));

        call.enqueue(new Callback<AuthenticationToken>() {
            @Override
            public void onResponse(@NonNull Call<AuthenticationToken> call, @NonNull Response<AuthenticationToken> response) {
                AuthenticationToken authenticationToken = response.body();

                if (authenticationToken == null) {
                    isLoggedIn = false;
                } else {

                    isLoggedIn = true;
                }
            }

            @Override
            public void onFailure(Call<AuthenticationToken> call, Throwable t) {
                isLoggedIn = false;
            }
        });

    }

    public static boolean isValidUser(String email, String password) {
        return false;
    }

}
