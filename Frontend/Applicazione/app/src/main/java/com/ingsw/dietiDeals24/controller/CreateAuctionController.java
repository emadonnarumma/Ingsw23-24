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
        CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Auction auction = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken())
                        .execute().body();
                addImagesIfPresent(images, auction);

                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    public static CompletableFuture<Boolean> createAuction(SilentAuction newAuction, List<Image> images) {
        CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Response<SilentAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken())
                        .execute();

                if (response.isSuccessful()) {
                    SilentAuction auction = response.body();
                    addImagesIfPresent(images, auction);
                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Token scaduto");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static CompletableFuture<Boolean> createAuction(DownwardAuction newAuction, List<Image> images) {
        CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Auction auction = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken())
                        .execute().body();
                addImagesIfPresent(images, auction);

                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    private static void addImagesIfPresent(List<Image> images, Auction auction) {
        if (!images.isEmpty()) {
            ImageAuctionBinder.bind(images, auction);
            insertImages(images);
        }
    }

    public static CompletableFuture<Boolean> insertImages(List<Image> images) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                InsertImagesDao insertImagesDao = retrofit.create(InsertImagesDao.class);
                for (Image image : images) {
                    Image returnImage = insertImagesDao.insertImage(image, TokenHolder.getAuthToken()).execute().body();
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