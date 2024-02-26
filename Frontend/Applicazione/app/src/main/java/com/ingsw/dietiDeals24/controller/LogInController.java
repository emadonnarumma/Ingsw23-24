package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.currentUser.CurrentUserDao;
import com.ingsw.dietiDeals24.network.login.LoginDao;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.login.LogInRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class LogInController implements RetroFitHolder {

    private LogInController() {
    }

    public static CompletableFuture<Boolean> login(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                LoginDao loginDao = retrofit.create(LoginDao.class);
                CurrentUserDao currentUserDao = retrofit.create(CurrentUserDao.class);

                LogInRequest request = new LogInRequest(email, password);
                Response<TokenHolder> response = loginDao.login(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    TokenHolder tokenHolder = TokenHolder.getInstance();
                    tokenHolder.setToken(response.body().getToken());
                    Role role = currentUserDao.getRole(email, TokenHolder.getAuthToken()).execute().body();
                    if (role == Role.BUYER) {
                        UserHolder.user = currentUserDao.getBuyerByEmail(email, TokenHolder.getAuthToken()).execute().body();
                        MyAuctionsController.getReverseAuctions(email);
                    } else if (role == Role.SELLER) {
                        UserHolder.user = currentUserDao.getSellerByEmail(email, TokenHolder.getAuthToken()).execute().body();
                        MyAuctionsController.getDownwardAuctions(email);
                        MyAuctionsController.getSilentAuctions(email);
                    }
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