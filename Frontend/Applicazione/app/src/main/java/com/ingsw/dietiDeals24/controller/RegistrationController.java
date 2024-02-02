package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.network.currentUser.CurrentUserDao;
import com.ingsw.dietiDeals24.network.registration.RegistrationDao;
import com.ingsw.dietiDeals24.network.registration.RegistrationRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class RegistrationController implements RetroFitHolder {
    public static User user = new User();

    private RegistrationController() {}

    public static CompletableFuture<Boolean> register() {
        RegistrationDao registrationDao = retrofit.create(RegistrationDao.class);
        CurrentUserDao currentUserDao = retrofit.create(CurrentUserDao.class);
        RegistrationRequest registrationRequest =  new RegistrationRequest(user.getName(), user.getEmail(), user.getPassword(), user.getBio(), user.getRegion());

        return CompletableFuture.supplyAsync(() -> {
            try {
                TokenHolder.instance = registrationDao.register(registrationRequest).execute().body();
                UserHolder.user = currentUserDao.getUser(
                        user.getEmail(), TokenHolder.getAuthToken()
                ).execute().body();
                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }
}