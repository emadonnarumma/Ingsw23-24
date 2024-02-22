package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.model.enumeration.Role;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.network.currentUser.CurrentUserDao;
import com.ingsw.dietiDeals24.network.registration.RegistrationDao;
import com.ingsw.dietiDeals24.network.registration.RegistrationRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class RegistrationController implements RetroFitHolder {
    public static User user = new User();

    private RegistrationController() {}

    public static CompletableFuture<Boolean> register() {
        RegistrationDao registrationDao = retrofit.create(RegistrationDao.class);
        CurrentUserDao currentUserDao = retrofit.create(CurrentUserDao.class);
        RegistrationRequest registrationRequest =  new RegistrationRequest(user.getName(), user.getEmail(), user.getPassword(), user.getBio(), user.getRegion());
        return CompletableFuture.supplyAsync(() -> {
            try {
                Response<TokenHolder> response = registrationDao.register(registrationRequest).execute();

                if (response.isSuccessful()) {
                    TokenHolder.instance = response.body();

                    String email = user.getEmail();
                    Role role = currentUserDao.getRole(email, TokenHolder.getAuthToken()).execute().body();
                    if (role == Role.BUYER) {
                        UserHolder.user = currentUserDao.getBuyerByEmail(email, TokenHolder.getAuthToken()).execute().body();
                    } else if (role == Role.SELLER) {
                        UserHolder.user = currentUserDao.getSellerByEmail(email, TokenHolder.getAuthToken()).execute().body();
                    }

                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("L'email è già in uso");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static CompletableFuture<Boolean> emailAlreadyExists(String email) {
        RegistrationDao registrationDao = retrofit.create(RegistrationDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Response<Boolean> response = registrationDao.emailAlreadyExists(email).execute();

                if (response.isSuccessful()) {
                    return response.body();

                } else {
                    throw new RuntimeException("Errore inaspettato");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
        });
    }
}