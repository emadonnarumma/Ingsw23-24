package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.createAuction.CreateAuctionDao;
import com.ingsw.dietiDeals24.network.createAuction.InsertImagesDao;
import com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder.ImageAuctionBinder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class CreateAuctionController implements RetroFitHolder {

    private CreateAuctionController() {
    }

    public static CompletableFuture<Boolean> createAuction(ReverseAuction newAuction, List<Image> images) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<ReverseAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    ReverseAuction auction = response.body();
                    ImageAuctionBinder.bind(images, auction);
                    addImagesIfPresent(images, auction);
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

    public static CompletableFuture<Boolean> createAuction(SilentAuction newAuction, List<Image> images) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<SilentAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    SilentAuction auction = response.body();
                    ImageAuctionBinder.bind(images, auction);
                    addImagesIfPresent(images, auction);
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

    public static CompletableFuture<Boolean> createAuction(DownwardAuction newAuction, List<Image> images) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<DownwardAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    DownwardAuction auction = response.body();
                    ImageAuctionBinder.bind(images, auction);
                    addImagesIfPresent(images, auction);
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

    private static void addImagesIfPresent(List<Image> images, Auction auction) {
        if (!images.isEmpty()) {
            insertImages(images);
        }
    }

    public static CompletableFuture<Boolean> insertImages(List<Image> images) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                InsertImagesDao insertImagesDao = retrofit.create(InsertImagesDao.class);
                for (Image image : images) {
                    Response<Image> response = insertImagesDao.insertImage(image, TokenHolder.getAuthToken()).execute();
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    public static boolean isValidDecrementAmount(double initialPrice, double decrementAmount) {
        return decrementAmount <= initialPrice * 0.15;
    }
}
