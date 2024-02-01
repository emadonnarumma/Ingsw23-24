package com.ingsw.dietiDeals24.ui.utility.auctionHolder;

import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.Image;

import java.util.List;

public class ImageAuctionBinder {

    public static void bind(List<Image> auctionImages, Auction auction) {
        bindAuctionToImages(auctionImages, auction);
        bindImagesToAuction(auctionImages, auction);
    }

    private static void bindAuctionToImages(List<Image> auctionImages, Auction auction) {
        for (Image image : auctionImages) {
            image.setAuction(auction);
        }
    }

    private static void bindImagesToAuction(List<Image> auctionImages, Auction auction) {
        auction.setImages(auctionImages);
    }
}
