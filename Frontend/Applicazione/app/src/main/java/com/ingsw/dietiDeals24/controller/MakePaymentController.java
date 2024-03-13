package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.Bid;
import com.ingsw.dietiDeals24.model.CreditCard;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.enumeration.BidStatus;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MakeBidDao;
import com.ingsw.dietiDeals24.network.dao.MakePaymentDao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class MakePaymentController implements RetroFitHolder {

    private static Auction auction;

    private static Bid bid;

    public static Bid getBid() {
        return bid;
    }

    public static void setBid(Bid bid) {
        MakePaymentController.bid = bid;
    }

    public static Auction getAuction() {
        return auction;
    }

    public static void setAuction(Auction auction) {
        MakePaymentController.auction = auction;
    }

    private static CreditCard creditCard;
    public static CreditCard getCreditCard() {
        return creditCard;
    }

    public static void setCreditCard(String cardNumber, String ownerName, String ownerSurname, String cvv, String expirationDate) {

        creditCard = new CreditCard(cardNumber, ownerName, ownerSurname, cvv, expirationDate);
    }

    public static CompletableFuture<DownwardBid> makeDownwardBid() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MakePaymentDao makePaymentDao = retrofit.create(MakePaymentDao.class);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
                Date date = new Date();
                String formattedTimestamp = sdf.format(date);

                DownwardAuction downwardAuction = (DownwardAuction) auction;

                DownwardBid downwardBid = new DownwardBid(downwardAuction.getCurrentPrice(), BidStatus.PENDING, formattedTimestamp, UserHolder.getBuyer(), downwardAuction);
                Response<DownwardBid> response = makePaymentDao.makeDownwardBid(downwardBid, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return null;
        });
    }

    public static CompletableFuture<Bid> payBid() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MakePaymentDao makePaymentDao = retrofit.create(MakePaymentDao.class);
                Response<Bid> response = makePaymentDao.payBid(bid.getIdBid(), TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return null;
        });
    }
}
