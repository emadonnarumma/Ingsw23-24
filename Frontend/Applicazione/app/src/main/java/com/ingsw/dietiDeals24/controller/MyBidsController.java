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
    private static boolean updatedSilent = false;
    private static boolean updatedDownward = false;
    private static boolean updatedReverse = false;

    public static void setUpdatedSilent(boolean updatedSilent) {
        MyBidsController.updatedSilent = updatedSilent;
    }

    public static void setUpdatedDownward(boolean updatedDownward) {
        MyBidsController.updatedDownward = updatedDownward;
    }

    public static void setUpdatedReverse(boolean updatedReverse) {
        MyBidsController.updatedReverse = updatedReverse;
    }

    public static void setUpdatedAll(boolean b) {
        updatedSilent = b;
        updatedDownward = b;
        updatedReverse = b;
    }

    public static CompletableFuture<List<ReverseBid>> getReverseBids(String email) {
        return CompletableFuture.supplyAsync(() -> {
            if (!UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }
            if (!updatedReverse) {
                try {
                    MyBidsDao myBidsDao = retrofit.create(MyBidsDao.class);
                    Response<List<ReverseBid>> response = myBidsDao.getReverseBids(email, TokenHolder.getAuthToken()).execute();
                    List<ReverseBid> bids = response.body();

                    if (response.isSuccessful()) {
                        updatedReverse = true;
                        reverseBids = bids;
                        return reverseBids;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return reverseBids;
            }
        });
    }

    public static CompletableFuture<List<SilentBid>> getSilentBids(String email) {
        return CompletableFuture.supplyAsync(() -> {
            if (UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }
            if (!updatedSilent) {
                try {
                    MyBidsDao myBidsDao = retrofit.create(MyBidsDao.class);
                    Response<List<SilentBid>> response = myBidsDao.getSilentBids(email, TokenHolder.getAuthToken()).execute();
                    List<SilentBid> bids = response.body();

                    if (response.isSuccessful()) {
                        updatedSilent = true;
                        silentBids = bids;
                        return silentBids;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return silentBids;
            }
        });
    }

    public static CompletableFuture<List<DownwardBid>> getDownwardBids(String email) {
        return CompletableFuture.supplyAsync(() -> {
            if (UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }
            if (!updatedDownward) {
                try {
                    MyBidsDao myBidsDao = retrofit.create(MyBidsDao.class);
                    Response<List<DownwardBid>> response = myBidsDao.getDownwardBids(email, TokenHolder.getAuthToken()).execute();
                    List<DownwardBid> bids = response.body();

                    if (response.isSuccessful()) {
                        updatedDownward = true;
                        downwardBids = bids;
                        return downwardBids;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return downwardBids;
            }
        });
    }
}
