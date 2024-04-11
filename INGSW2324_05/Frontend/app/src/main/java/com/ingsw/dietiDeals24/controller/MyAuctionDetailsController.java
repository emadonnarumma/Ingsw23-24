package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MyAuctionDetailsDao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
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
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<DownwardBid> response = myAuctionDetailsDao.getWinningDownwardBidByAuctionId(idAuction, TokenHolder.getAuthToken()).execute();

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
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<ReverseBid> response = myAuctionDetailsDao.getWinningReverseBidByAuctionId(idAuction, TokenHolder.getAuthToken()).execute();

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
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<SilentBid> response = myAuctionDetailsDao.getWinningSilentBidByAuctionId(idAuction, TokenHolder.getAuthToken()).execute();

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

    public static CompletableFuture<List<SilentBid>> getInProgressSilentBidsByAuctionId(Integer idBid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<List<SilentBid>> response = myAuctionDetailsDao.getInProgressSilentBidsByAuctionId(idBid, TokenHolder.getAuthToken()).execute();

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

    public static CompletableFuture<Boolean> deleteBid(Integer idBid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<Boolean> response = myAuctionDetailsDao.deleteBid(idBid, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Token scaduto");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static CompletableFuture<Boolean> deleteAuction(Integer idAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<Boolean> response = myAuctionDetailsDao.deleteAuction(idAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyAuctionsController.setUpdatedAll(false);
                    return response.body();
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
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<List<SilentBid>> response = myAuctionDetailsDao.getAllSilentBidsBySilentAuctionId(auctionId, TokenHolder.getAuthToken()).execute();

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
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<ReverseBid> response = myAuctionDetailsDao.getMinPricedReverseBidsByAuctionId(auctionId, TokenHolder.getAuthToken()).execute();

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
                MyAuctionDetailsDao myAuctionDetailsDao = retrofit.create(MyAuctionDetailsDao.class);
                Response<Boolean> response = myAuctionDetailsDao.acceptBid(id, TokenHolder.getAuthToken()).execute();

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
        Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        long currentTimestamp = currentCalendar.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
        Date bidDate = null;

        try {
            bidDate = sdf.parse(silentBid.getTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long bidTimestamp;
        if (bidDate != null) {
            bidTimestamp = bidDate.getTime();
        } else {
            bidTimestamp = 0;
        }

        long withdrawalTime = silentBid.getSilentAuction().getWithdrawalTime() * 1000;
        long remainingMillis = withdrawalTime - (currentTimestamp - bidTimestamp);
        long remainingSeconds = remainingMillis / 1000L;


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

        return "Decremento ogni: " + formattedTime;
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
}



