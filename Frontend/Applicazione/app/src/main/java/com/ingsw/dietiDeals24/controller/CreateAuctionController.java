package com.ingsw.dietiDeals24.controller;

import android.net.Uri;

import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.createAuction.CreateAuctionDao;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CreateAuctionController implements RetroFitHolder {
    public static Auction newAuction = new Auction();

    private CreateAuctionController() {}

    public static CompletableFuture<Boolean> createAuction() {
        CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);

        return CompletableFuture.supplyAsync(() -> {
            try {
                createAuctionDao.createAuction(newAuction).execute();
                return true;
            } catch (IOException e) {
                return false;
            }
        });
    }

    public static void setNewAuctionTitle(String title) {
        newAuction.setTitle(title);
    }

    public static void setNewAuctionDescription(String description) {
        newAuction.setDescription(description);
    }

    public static void setNewAuctionImages(List<Uri> images) {
        newAuction.setImages(images);
    }

    public static void setNewAuctionWear(Wear wear) {
        newAuction.setWear(wear);
    }

    public static void setNewAuctionCategory(Category category) {
        newAuction.setCategory(category);
    }
}