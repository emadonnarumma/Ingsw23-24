package com.ingsw.dietiDeals24.controller;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.ReverseBidFormState;
import com.ingsw.dietiDeals24.controller.formstate.SilentBidFormState;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.ReverseBid;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.model.enumeration.BidStatus;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MakeBidDao;
import com.ingsw.dietiDeals24.network.dao.MyAuctiondDetailsDao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class MakeBidController implements RetroFitHolder {
    private static SilentAuction silentAuction;
    private static ReverseAuction reverseAuction;
    private static DownwardAuction downwardAuction;
    private static MutableLiveData<SilentBidFormState> silentBidFormState = new MutableLiveData<>();
    private static MutableLiveData<ReverseBidFormState> reverseBidFormState = new MutableLiveData<>();

    public static MutableLiveData<SilentBidFormState> getSilentBidFormState() {
        return silentBidFormState;
    }

    public static MutableLiveData<ReverseBidFormState> getReverseBidFormState() {
        return reverseBidFormState;
    }

    public static void reverseBidInputChanged(Context context, String currentBid, double minBid) {
        if (!isReverseBidValid(currentBid, minBid)) {
            reverseBidFormState.setValue(new ReverseBidFormState(context.getString(R.string.reverse_bid_error)));
        } else {
            reverseBidFormState.setValue(new ReverseBidFormState(true));
        }
    }

    public static void silentBidInputChanged(Context context, String bid) {
        if (!isSilentBidValid(bid)) {
            silentBidFormState.setValue(new SilentBidFormState(context.getString(R.string.silent_bid_error)));
        } else {
            silentBidFormState.setValue(new SilentBidFormState(true));
        }
    }

    private static boolean isSilentBidValid(String bid) {
        if (bid == null) {
            return false;
        }
        if (bid.isEmpty()) {
            return false;
        }
        if (bid.equals(".")) {
            return false;
        }
        if (bid.matches("0*\\.?0*")) {
            return false;
        }
        if (bid.startsWith(".") || bid.endsWith(".")) { // aggiunto questo controllo
            return false;
        }
        return bid.length() <= 10;
    }

    private static boolean isReverseBidValid(String bid, double minBid) {
        if (bid == null) {
            return false;
        }
        if (bid.isEmpty()) {
            return false;
        }
        if (bid.equals(".")) {
            return false;
        }
        if (bid.matches("0*\\.?0*")) {
            return false;
        }
        if (bid.startsWith(".") || bid.endsWith(".")) { // aggiunto questo controllo
            return false;
        }
        if (Double.parseDouble(bid) >= minBid || bid.equals("0")) {
            return false;
        }
        return bid.length() <= 10;
    }

    public static SilentAuction getSilentAuction() {
        return silentAuction;
    }

    public static void setSilentAuction(SilentAuction silentAuction) {
        MakeBidController.silentAuction = silentAuction;
    }

    public static ReverseAuction getReverseAuction() {
        return reverseAuction;
    }

    public static void setReverseAuction(ReverseAuction reverseAuction) {
        MakeBidController.reverseAuction = reverseAuction;
    }

    public static DownwardAuction getDownwardAuction() {
        return downwardAuction;
    }

    public static void setDownwardAuction(DownwardAuction downwardAuction) {
        MakeBidController.downwardAuction = downwardAuction;
    }

    private MakeBidController() {}

    public static CompletableFuture<Boolean> makeSilentBid(double amount) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MakeBidDao makeBidDao = retrofit.create(MakeBidDao.class);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
                Date date = new Date();
                String formattedTimestamp = sdf.format(date);
                SilentBid silentBid = new SilentBid(amount, BidStatus.PENDING, formattedTimestamp, UserHolder.getBuyer(), silentAuction);
                Response<SilentBid> response = makeBidDao.makeSilentBid(silentBid, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyBidsController.setUpdatedSilent(false);
                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static CompletableFuture<Boolean> makeReverseBid(double amount) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MakeBidDao makeBidDao = retrofit.create(MakeBidDao.class);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
                Date date = new Date();
                String formattedTimestamp = sdf.format(date);
                ReverseBid reverseBid = new ReverseBid(amount, BidStatus.PENDING, formattedTimestamp, UserHolder.getSeller(), reverseAuction);
                Response<ReverseBid> response = makeBidDao.makeReverseBid(reverseBid, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyBidsController.setUpdatedReverse(false);
                    return true;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Errore di autenticazione");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return false;
        });
    }

    public static CompletableFuture<ReverseBid> getCurrentReverseBid() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MyAuctiondDetailsDao myAuctiondDetailsDao = retrofit.create(MyAuctiondDetailsDao.class);
                Response<ReverseBid> response = myAuctiondDetailsDao.getMinPricedReverseBidsByAuctionId(reverseAuction.getIdAuction(), TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else if (response.code() == 404) {
                    return null;
                } else if (response.code() == 403) {
                    throw new AuthenticationException("Token scaduto");
                }

            } catch (IOException e) {
                throw new ConnectionException("Errore di connessione");
            }
            return null;
        });
    }
}
