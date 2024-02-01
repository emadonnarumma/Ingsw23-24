package com.ingsw.dietiDeals24.controller;

import android.net.Uri;

import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.Image;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.createAuction.CreateAuctionDao;
import com.ingsw.dietiDeals24.network.createAuction.InsertImagesDao;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateAuctionController implements RetroFitHolder {

    private CreateAuctionController() {}

    public static CompletableFuture<Boolean> createAuction(Auction newAuction) {
        CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Image> images = newAuction.getImages();
                newAuction.setImages(null);

                createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken())
                        .execute().body();

                insertImages(images);
                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    public static CompletableFuture<Boolean> insertImages(List<Image> images) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                InsertImagesDao insertImagesDao = retrofit.create(InsertImagesDao.class);
                insertImagesDao.insertImage(images, TokenHolder.getAuthToken()).execute();
                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }
}