package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MyAuctiondDetailsDao;

import java.io.IOException;
import java.util.Calendar;
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

    private MyAuctionDetailsController() {
    }

    public static CompletableFuture<DownwardBid> getWinningDownwardBidByAuctionId(Integer idAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctiondDetailsDao myAuctiondDetailsDao = retrofit.create(MyAuctiondDetailsDao.class);
                Response<DownwardBid> response = myAuctiondDetailsDao.getWinningDownwardBidByAuctionId(idAuction, TokenHolder.getAuthToken()).execute();

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

    public static CompletableFuture<ReverseBid> getWinningReverseBidByAuctionId(Integer idAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctiondDetailsDao myAuctiondDetailsDao = retrofit.create(MyAuctiondDetailsDao.class);
                Response<ReverseBid> response = myAuctiondDetailsDao.getWinningReverseBidByAuctionId(idAuction, TokenHolder.getAuthToken()).execute();

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

    public static CompletableFuture<SilentBid> getWinningSilentBidByAuctionId(Integer idAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctiondDetailsDao myAuctiondDetailsDao = retrofit.create(MyAuctiondDetailsDao.class);
                Response<SilentBid> response = myAuctiondDetailsDao.getWinningSilentBidByAuctionId(idAuction, TokenHolder.getAuthToken()).execute();

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

    public static String getRemainingTime(String nextDecrement) {
        Calendar nextDecrementTime = convertStringToCalendar(nextDecrement);
        Calendar currentTime = Calendar.getInstance();
        long remainingSeconds = (nextDecrementTime.getTimeInMillis() - currentTime.getTimeInMillis()) / 1000;

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

    public static Calendar convertStringToCalendar(String timestamp) {
        String[] parts = timestamp.split(" ");
        String[] dateParts = parts[0].split("-");
        String[] timeParts = parts[1].split(":");

        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]) - 1;
        int day = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        int second = Integer.parseInt(timeParts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);

        return calendar;
    }
}



