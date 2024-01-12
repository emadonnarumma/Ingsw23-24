package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.network.IPHolder;
import com.ingsw.dietiDeals24.network.login.LoginDao;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.model.User;
import com.ingsw.dietiDeals24.network.registration.RegistrationDao;
import com.ingsw.dietiDeals24.network.registration.RegistrationRequest;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationController implements IPHolder {
    public static User user = new User();

    private RegistrationController() {}

    public static Future<Boolean> register() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegistrationDao registrationDao = retrofit.create(RegistrationDao.class);
        RegistrationRequest registrationRequest =  new RegistrationRequest(user.getName(), user.getEmail(), user.getPassword(), user.getBio(), user.getRegion());
        Call<TokenHolder> call = registrationDao.register(registrationRequest);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        return executorService.submit(() -> {
            try {
                Response<TokenHolder> response = call.execute();
                TokenHolder tokenHolder = response.body();
                return tokenHolder != null;
            } catch (IOException e) {
                return false;
            }
        });
    }
}
