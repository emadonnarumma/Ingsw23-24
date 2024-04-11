package com.ingsw.dietiDeals24.controller;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.MakePaymentFormState;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.Auction;
import com.ingsw.dietiDeals24.model.Bid;
import com.ingsw.dietiDeals24.model.CreditCard;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.DownwardBid;
import com.ingsw.dietiDeals24.model.enumeration.BidStatus;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.MakePaymentDao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class MakePaymentController implements RetroFitHolder {

    private static MutableLiveData<MakePaymentFormState> makePaymentFormState = new MutableLiveData<>();

    private static Auction auction;

    private static Bid bid;
    private static CreditCard creditCard;

    public static MutableLiveData<MakePaymentFormState> getMakePaymentFormState() {
        return makePaymentFormState;
    }



    public static void makePaymentDataChanged(String cardCode, String name, String surname, String expirationDate, String cvv, Context context) {
        if (!isCardCodeValid(cardCode)) {
            makePaymentFormState.setValue(new MakePaymentFormState(context.getString(R.string.card_code_error), null, null, null, null));
        } else if (!isNameValid(name)) {
            makePaymentFormState.setValue(new MakePaymentFormState(null, context.getString(R.string.name_error), null, null, null));
        } else if (!isSurnameValid(surname)) {
            makePaymentFormState.setValue(new MakePaymentFormState(null, null, context.getString(R.string.surname_error), null, null));
        } else if (!isExpirationDateValid(expirationDate)) {
            makePaymentFormState.setValue(new MakePaymentFormState(null, null, null, context.getString(R.string.expiration_date_error), null));
        } else if (!isCvvValid(cvv)) {
            makePaymentFormState.setValue(new MakePaymentFormState(null, null, null, null, context.getString(R.string.cvv_error)));
        } else {
            makePaymentFormState.setValue(new MakePaymentFormState(true));
        }
    }

    private static boolean isCvvValid(String cvv) {

        return cvv.length() == 3;
    }

    private static boolean isExpirationDateValid(String expirationDate) {
        if (expirationDate.length() != 7) {
            return false;
        }

        String[] parts = expirationDate.split("/");
        if (parts.length != 2) {
            return false;
        }

        String monthPart = parts[0];
        String yearPart = parts[1];

        int month;
        int year;

        try {
            month = Integer.parseInt(monthPart);
            year = Integer.parseInt(yearPart);
        } catch (NumberFormatException e) {
            return false;
        }

        if (month < 1 || month > 12) {
            return false;
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar.MONTH returns 0-11 for Jan-Dec

        if (year < currentYear) {
            return false;
        } else if (year == currentYear && month < currentMonth) {
            return false;
        }

        return true;
    }

    private static boolean isSurnameValid(String surname) {

        int surnameLength = surname.length();

        return surnameLength > 0 && surnameLength <= 20;
    }

    private static boolean isNameValid(String name) {

        int nameLength = name.length();

        return nameLength > 0 && nameLength <= 20;
    }

    private static boolean isCardCodeValid(String cardCode) {

        return cardCode.length() == 19;
    }


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
                    SearchAuctionsController.setUpdatedAll(false);
                    MyAuctionsController.setUpdatedAll(false);
                    MyBidsController.setUpdatedAll(false);

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
                    SearchAuctionsController.setUpdatedAll(false);
                    MyAuctionsController.setUpdatedAll(false);
                    MyBidsController.setUpdatedAll(false);
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
