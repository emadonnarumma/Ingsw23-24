package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.login.LoginDao;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.login.LogInRequest;
import com.ingsw.dietiDeals24.model.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class LogInController implements RetroFitHolder {
    public static User loggedUser;

    private LogInController() {}

    public static CompletableFuture<Boolean> login(String email, String password) {

        LoginDao loginDao = retrofit.create(LoginDao.class);
        LogInRequest request = new LogInRequest(email, password);

        return CompletableFuture.supplyAsync(() -> {
            try {
                TokenHolder tokenHolder = loginDao.login(request).execute().body();
                return tokenHolder != null;
            } catch (IOException e) {
                return false;
            }
        });
    }
}