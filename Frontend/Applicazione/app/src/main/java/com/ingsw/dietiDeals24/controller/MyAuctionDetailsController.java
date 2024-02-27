package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MyAuctiondDetailsDao;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class MyAuctionDetailsController extends MyAuctionsController implements RetroFitHolder {
    private static Auction auction;

    public static Auction getAuction() {
        return auction;
    }

    public static void setAuction(Auction auction) {
        MyAuctionDetailsController.auction = auction;
    }

    private MyAuctionDetailsController() {}

    public static CompletableFuture<Boolean> deleteAuction(Integer idAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctiondDetailsDao myAuctiondDetailsDao = retrofit.create(MyAuctiondDetailsDao.class);
                Response<Void> response = myAuctiondDetailsDao.deleteAuction(idAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyAuctionsController.setUpdatedAll(false);
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

    public static CompletableFuture<List<SilentBid>> getAllSilentBidsByAuctionId(Integer auctionId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctiondDetailsDao myAuctiondDetailsDao = retrofit.create(MyAuctiondDetailsDao.class);
                Response<List<SilentBid>> response = myAuctiondDetailsDao.getAllSilentBidsBySilentAuctionId(auctionId, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Token scaduto");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return null;
        });
    }

    public static CompletableFuture<ReverseBid> getMinPricedReverseBidByAuctionId(Integer auctionId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctiondDetailsDao myAuctiondDetailsDao = retrofit.create(MyAuctiondDetailsDao.class);
                Response<ReverseBid> response = myAuctiondDetailsDao.getMinPricedReverseBidsByAuctionId(auctionId, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Token scaduto");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return null;
        });
    }



    public static CompletableFuture<Boolean> acceptBid(int id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctiondDetailsDao myAuctiondDetailsDao = retrofit.create(MyAuctiondDetailsDao.class);
                Response<Boolean> response = myAuctiondDetailsDao.acceptBid(id, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                        MyAuctionsController.setUpdatedAll(false);
                    return response.body();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
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

    public static String getNextDecrementTimeText(DownwardAuction auction) {
        long totalSeconds = auction.getDecrementTime();

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

        return "Tempo del prossimo decremento  : " + formattedTime ;
    }

    public static String getRemainingWithdrawalTimeText(SilentBid silentBid) {
        long currentTimestamp = System.currentTimeMillis() / 1000L;
        long bidTimestamp = silentBid.getTimestamp().getTime() / 1000L;
        long withdrawalTime = silentBid.getSilentAuction().getWithdrawalTime();
        long remainingSeconds = bidTimestamp + withdrawalTime - currentTimestamp;

        long months = remainingSeconds / (30 * 24 * 60 * 60);
        remainingSeconds %= 30 * 24 * 60 * 60;

        long days = remainingSeconds / (24 * 60 * 60);
        remainingSeconds %= 24 * 60 * 60;

        long hours = remainingSeconds / (60 * 60);
        remainingSeconds %= 60 * 60;

        long minutes = remainingSeconds / 60;

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

        return "L'utente ha : " + formattedTime + " per ritirare l'offerta";
    }
}
