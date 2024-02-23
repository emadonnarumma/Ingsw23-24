package com.ingsw.dietiDeals24.controller;

import android.util.Log;

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
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class MyAuctionsController implements RetroFitHolder {
    private static List<SilentAuction> silentAuctions = new ArrayList<>();
    private static List<ReverseAuction> reverseAuctions = new ArrayList<>();
    private static List<DownwardAuction> downwardAuctions = new ArrayList<>();
    private static boolean updatedSilent = false;
    private static boolean updatedDownward = false;
    private static boolean updatedReverse = false;

    public static void setUpdatedSilent(boolean updatedSilent) {
        MyAuctionsController.updatedSilent = updatedSilent;
    }

    public static void setUpdatedDownward(boolean updatedDownward) {
        MyAuctionsController.updatedDownward = updatedDownward;
    }

    public static void setUpdatedReverse(boolean updatedReverse) {
        MyAuctionsController.updatedReverse = updatedReverse;
    }


    protected static void setUpdatedAll(boolean b) {
        updatedSilent = b;
        updatedDownward = b;
        updatedReverse = b;
    }

    protected MyAuctionsController() {
    }

    public static CompletableFuture<List<SilentAuction>> getSilentAuctions(String email) {
        return CompletableFuture.supplyAsync(() -> {
            if (!UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }
            if (!updatedSilent) {
                try {
                    MyAuctionsDao myAuctionsDao = retrofit.create(MyAuctionsDao.class);
                    Response<List<SilentAuction>> response = myAuctionsDao.getSilentAuctions(email, TokenHolder.getAuthToken()).execute();
                    List<SilentAuction> auctions = response.body();
                    for (SilentAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = myAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        updatedSilent = true;
                        silentAuctions = auctions;
                        return silentAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return silentAuctions;
            }
        });
    }

    public static CompletableFuture<List<ReverseAuction>> getReverseAuctions(String email) {
        return CompletableFuture.supplyAsync(() -> {
            if (UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }
            if (!updatedReverse) {
                try {
                    MyAuctionsDao myAuctionsDao = retrofit.create(MyAuctionsDao.class);
                    Response<List<ReverseAuction>> response = myAuctionsDao.getReverseAuctions(email, TokenHolder.getAuthToken()).execute();
                    List<ReverseAuction> auctions = response.body();
                    for (ReverseAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = myAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        updatedReverse = true;
                        reverseAuctions = auctions;
                        return reverseAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return reverseAuctions;
            }
        });
    }

    public static CompletableFuture<List<DownwardAuction>> getDownwardAuctions(String email) {
        return CompletableFuture.supplyAsync(() -> {
            if (!UserHolder.user.isSeller()) {
                return new ArrayList<>();
            }
            if (!updatedDownward) {
                try {
                    MyAuctionsDao myAuctionsDao = retrofit.create(MyAuctionsDao.class);
                    Response<List<DownwardAuction>> response = myAuctionsDao.getDownwardAuctions(email, TokenHolder.getAuthToken()).execute();
                    List<DownwardAuction> auctions = response.body();

                    for (DownwardAuction auction : auctions) {
                        Response<List<Image>> imagesResponse = myAuctionsDao.getAllAuctionImages(auction.getIdAuction(), TokenHolder.getAuthToken()).execute();
                        if (imagesResponse.isSuccessful())
                            auction.setImages(imagesResponse.body());
                    }

                    if (response.isSuccessful()) {
                        updatedDownward = true;
                        downwardAuctions = auctions;
                        return downwardAuctions;
                    } else if (response.code() == 403) {
                        return new ArrayList<>();
                    }

                } catch (IOException e) {
                    throw new ConnectionException("Errore di connessione");
                }
                return new ArrayList<>();
            } else {
                return downwardAuctions;
            }
        });
    }

    public static String getFormattedExpirationDate(SilentAuction auction) {
        String expirationDateString = auction.getExpirationDate();
        String year = expirationDateString.substring(6, 10);
        String month = expirationDateString.substring(3, 5);
        String day = expirationDateString.substring(0, 2);
        return day + "/" + month + "/" + year;
    }

    public static String getFormattedExpirationDate(ReverseAuction auction) {
        String expirationDateString = auction.getExpirationDate();
        String year = expirationDateString.substring(6, 10);
        String month = expirationDateString.substring(3, 5);
        String day = expirationDateString.substring(0, 2);
        return day + "/" + month + "/" + year;
    }
}
