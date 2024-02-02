package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.currentUser.CurrentUserDao;
import com.ingsw.dietiDeals24.network.login.LoginDao;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.login.LogInRequest;
import com.ingsw.dietiDeals24.model.User;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class LogInController implements RetroFitHolder {

    private LogInController() {
    }

    public static CompletableFuture<Boolean> login(String email, String password) {

        LoginDao loginDao = retrofit.create(LoginDao.class);
        CurrentUserDao currentUserDao = retrofit.create(CurrentUserDao.class);
        LogInRequest request = new LogInRequest(email, password);

        return CompletableFuture.supplyAsync(() -> {
            try {
                Response<TokenHolder> response = loginDao.login(request).execute();
                if (response.isSuccessful()) {
                    TokenHolder.instance = response.body();
                    UserHolder.user = currentUserDao.getUser(email, TokenHolder.getAuthToken()).execute().body();
                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Credenziali errate");
                }
            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }
}