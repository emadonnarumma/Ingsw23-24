package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.createAuction.CreateAuctionDao;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class CreateAuctionController implements RetroFitHolder {

    private CreateAuctionController() {
    }

    public static CompletableFuture<Boolean> createAuction(ReverseAuction newAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<ReverseAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyAuctionsController.setUpdatedReverse(false);
                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static CompletableFuture<Boolean> createAuction(SilentAuction newAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<SilentAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyAuctionsController.setUpdatedSilent(false);
                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static CompletableFuture<Boolean> createAuction(DownwardAuction newAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<DownwardAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyAuctionsController.setUpdatedDownward(false);
                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static boolean isValidDecrementAmount(double initialPrice, double decrementAmount) {
        return decrementAmount <= initialPrice * 0.15;
    }
}
