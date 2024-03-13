package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Bid;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MyBidsDao;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class MyBidDetailsController implements RetroFitHolder {

    private static Bid bid;

    public static Bid getBid() {
        return bid;
    }

    public static void setBid(Bid bid) {
        MyBidDetailsController.bid = bid;
    }

    private MyBidDetailsController() {

    }

    public static CompletableFuture<Boolean> deleteBid(Integer idBid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyBidsDao myBidsDao = retrofit.create(MyBidsDao.class);
                Response<Boolean> response = myBidsDao.deleteBid(idBid, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Token scaduto");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }
}
