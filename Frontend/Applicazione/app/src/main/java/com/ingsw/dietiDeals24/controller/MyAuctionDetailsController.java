package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;

public class MyAuctionDetailsController implements RetroFitHolder {
    private MyAuctionDetailsController() {
    }

    public static String getWithdrawalTimeText(SilentAuction auction) {
        long totalSeconds = auction.getWithdrawalTime();

        long months = totalSeconds / (30 * 24 * 60 * 60);
        totalSeconds %= 30 * 24 * 60 * 60;

        long days = totalSeconds / (24 * 60 * 60);
        totalSeconds %= 24 * 60 * 60;

        long hours = totalSeconds / (60 * 60);
        totalSeconds %= 60 * 60;

        long minutes = totalSeconds / 60;

        String formattedTime = "";
        if (months > 0) {
            formattedTime += months + "M ";
        }
        if (days > 0) {
            formattedTime += days + "G ";
        }
        if (hours > 0) {
            formattedTime += hours + "H ";
        }
        if (minutes > 0) {
            formattedTime += minutes + "Min";
        }

        return "I compratori avevano : " + formattedTime + " per ritirare le offerte";
    }
}
