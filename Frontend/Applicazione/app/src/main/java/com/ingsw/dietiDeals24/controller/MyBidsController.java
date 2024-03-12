package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MyBidsDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class MyBidsController implements RetroFitHolder {

    private static List<SilentBid> silentBids = new ArrayList<>();
    private static List<DownwardBid> downwardBids = new ArrayList<>();
    private static List<ReverseBid> reverseBids = new ArrayList<>();

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


    public static CompletableFuture<List<ReverseBid>> getReverseBids(String email) {
        return CompletableFuture.supplyAsync(() -> {

            if (!UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }

            try {

                MyBidsDao myBidsDao = retrofit.create(MyBidsDao.class);
                Response<List<ReverseBid>> response = myBidsDao.getReverseBids(email, TokenHolder.getAuthToken()).execute();
                List<ReverseBid> bids = response.body();


                if (response.isSuccessful()) {
                    reverseBids = bids;
                    return reverseBids;

                } else if (response.code() == 403) {
                    return new ArrayList<>();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();

        });
    }

    public static CompletableFuture<List<SilentBid>> getSilentBids(String email) {
        return CompletableFuture.supplyAsync(() -> {

            if (UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }

            try {

                MyBidsDao myBidsDao = retrofit.create(MyBidsDao.class);
                Response<List<SilentBid>> response = myBidsDao.getSilentBids(email, TokenHolder.getAuthToken()).execute();
                List<SilentBid> bids = response.body();


                if (response.isSuccessful()) {
                    silentBids = bids;
                    return silentBids;

                } else if (response.code() == 403) {
                    return new ArrayList<>();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();

        });
    }

    public static CompletableFuture<List<DownwardBid>> getDownwardBids(String email) {
        return CompletableFuture.supplyAsync(() -> {

            if (UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }

            try {

                MyBidsDao myBidsDao = retrofit.create(MyBidsDao.class);
                Response<List<DownwardBid>> response = myBidsDao.getDownwardBids(email, TokenHolder.getAuthToken()).execute();
                List<DownwardBid> bids = response.body();


                if (response.isSuccessful()) {
                    downwardBids = bids;
                    return downwardBids;

                } else if (response.code() == 403) {
                    return new ArrayList<>();
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return new ArrayList<>();

        });
    }
}
