package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.login.LoginDao;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.login.LogInRequest;
import com.ingsw.dietiDeals24.model.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class LogInController implements RetroFitHolder {

    private LogInController() {
    }

    public static CompletableFuture<Boolean> login(String email, String password) {

        LoginDao loginDao = retrofit.create(LoginDao.class);
        LogInRequest request = new LogInRequest(email, password);

        return CompletableFuture.supplyAsync(() -> {
            try {
                TokenHolder.instance = loginDao.login(request).execute().body();
                UserHolder.user = loginDao.getUser(email, TokenHolder.getAuthToken()).execute().body();
                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }
}