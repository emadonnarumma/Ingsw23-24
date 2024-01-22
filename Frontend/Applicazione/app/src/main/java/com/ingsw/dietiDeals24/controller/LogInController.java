package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.network.IPHolder;
import com.ingsw.dietiDeals24.network.login.LoginDao;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.login.LogInRequest;
import com.ingsw.dietiDeals24.model.User;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInController implements IPHolder {
    public static User loggedUser;

    private LogInController() {}

    public static Future<Boolean> login(String email, String password) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginDao loginDao = retrofit.create(LoginDao.class);
        Call<TokenHolder> call = loginDao.login(new LogInRequest(email, password));

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        return executorService.submit(() -> {
            try {
                Response<TokenHolder> response = call.execute();
                TokenHolder tokenHolder = response.body();
                return tokenHolder != null;
            } catch (IOException e) {
                throw e;
            }
        });
    }
}
