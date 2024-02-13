package com.ingsw.dietiDeals24.ui.home.createAuction.auctionHolder;

import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.Image;

import java.util.List;

public class ImageAuctionBinder {

    public static void bind(List<Image> auctionImages, Auction auction) {
        bindAuctionToImages(auctionImages, auction);
    }

    private static void bindAuctionToImages(List<Image> auctionImages, Auction auction) {
        for (Image image : auctionImages) {
            image.setAuction(auction);
        }
    }
}
