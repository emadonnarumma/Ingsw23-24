package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;

public class SearchAuctionDetailsController implements RetroFitHolder {

    private static Auction auction;

    public static Auction getAuction() {
        return auction;
    }

    public static void setAuction(Auction auction) {
        SearchAuctionDetailsController.auction = auction;
    }
}
