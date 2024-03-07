package com.ingsw.dietiDeals24.controller;

import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.model.SilentBid;
import com.ingsw.dietiDeals24.model.enumeration.BidStatus;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MakeBidDao;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class MakeBidController implements RetroFitHolder {
    private static SilentAuction silentAuction;
    private static ReverseAuction reverseAuction;
    private static DownwardAuction downwardAuction;

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

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
                Date date = Date.from(Instant.now());
                String formattedTimestamp = sdf.format(date);
                Timestamp currentTimestamp = Timestamp.valueOf(formattedTimestamp);
                SilentBid silentBid = new SilentBid(amount, BidStatus.PENDING, currentTimestamp, UserHolder.getBuyer(), silentAuction);
                Response<SilentBid> response = makeBidDao.makeSilentBid(silentBid, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
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
}
