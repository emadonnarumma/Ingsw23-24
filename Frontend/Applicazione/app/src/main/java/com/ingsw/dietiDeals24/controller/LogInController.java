package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.authentication.AuthenticationDao;
import com.ingsw.dietiDeals24.authentication.AuthenticationToken;
import com.ingsw.dietiDeals24.authentication.LogInRequest;
import com.ingsw.dietiDeals24.model.User;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInController {
    public static User loggedUser;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private LogInController() {
    }

    public static Future<Boolean> login(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.27:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthenticationDao authenticationDao = retrofit.create(AuthenticationDao.class);
        Call<AuthenticationToken> call = authenticationDao.login(new LogInRequest(email, password));

        return executorService.submit(() -> {
            try {
                Response<AuthenticationToken> response = call.execute();
                AuthenticationToken authenticationToken = response.body();
                return authenticationToken != null;
            } catch (IOException e) {
                return false;
            }
        });
    }
}
