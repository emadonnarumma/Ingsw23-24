package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.myAuctions.MyAuctionsDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import retrofit2.Response;

public class MyAuctionsController implements RetroFitHolder {
    public static CompletableFuture<List<SilentAuction>> getSilentAuctions(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctionsDao myAuctionsDao = retrofit.create(MyAuctionsDao.class);
                Response<List<SilentAuction>> response = myAuctionsDao.getSilentAuctions(email, TokenHolder.getAuthToken()).execute();
                List<SilentAuction> auctions = response.body();
                for (SilentAuction auction : auctions) {
                    auction.setImages(myAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute().body());
                }

                if (response.isSuccessful()) {
                    return response.body();
                } else if (response.code() == 403) {
                    return new ArrayList<>();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();
        });
    }

    public static CompletableFuture<List<ReverseAuction>> getReverseAuctions(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctionsDao myAuctionsDao = retrofit.create(MyAuctionsDao.class);
                Response<List<ReverseAuction>> response = myAuctionsDao.getReverseAuctions(email, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();
        });
    }

    public static CompletableFuture<List<DownwardAuction>> getDownwardAuctions(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctionsDao myAuctionsDao = retrofit.create(MyAuctionsDao.class);
                Response<List<DownwardAuction>> response = myAuctionsDao.getDownwardAuctions(email, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();
        });
    }
}
