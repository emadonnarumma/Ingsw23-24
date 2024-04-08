package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.SearchAuctionDetailsDao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class SearchAuctionDetailsController implements RetroFitHolder {

    private static Auction auction;

    public static Auction getAuction() {
        return auction;
    }

    public static void setAuction(Auction auction) {
        SearchAuctionDetailsController.auction = auction;
    }

    public static CompletableFuture<ReverseBid> getMinReverseBid(Integer idAuction) {
        return CompletableFuture.supplyAsync(() -> {

            try {
                SearchAuctionDetailsDao searchAuctionsDetailsDao = retrofit.create(SearchAuctionDetailsDao.class);
                Response<ReverseBid> response = searchAuctionsDetailsDao.getMinReverseBid(idAuction, TokenHolder.getAuthToken()).execute();
                ReverseBid bid = response.body();

                if (response.isSuccessful()) {

                    return bid;

                } else if (response.code() == 404) {
                    return null;
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }

            return null;
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

        return formattedTime;
    }

    public static Calendar convertStringToCalendar(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return calendar;
    }

    public static String getRemainingDecrementTime(String nextDecrement) {
        Calendar nextDecrementTime = convertStringToCalendar(nextDecrement);
        Calendar currentTime = Calendar.getInstance();
        long remainingSeconds = (nextDecrementTime.getTimeInMillis() - currentTime.getTimeInMillis()) / 1000;

        if (remainingSeconds < 0) {
            return "Il decremento è già avvenuto";
        }

        long months = remainingSeconds / (30 * 24 * 60 * 60);
        remainingSeconds %= 30 * 24 * 60 * 60;

        long days = remainingSeconds / (24 * 60 * 60);
        remainingSeconds %= 24 * 60 * 60;

        long hours = remainingSeconds / (60 * 60);
        remainingSeconds %= 60 * 60;

        long minutes = remainingSeconds / 60;

        String formattedTime = "Prossimo decremento tra: ";
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

        return formattedTime;
    }

    public static String getDecrementTimeText(long seconds) {
        long totalSeconds = seconds;

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

        return "Decremento ogni  : " + formattedTime;
    }
}
