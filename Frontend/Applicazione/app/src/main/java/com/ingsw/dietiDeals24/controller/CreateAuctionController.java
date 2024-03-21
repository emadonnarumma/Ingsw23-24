package com.ingsw.dietiDeals24.controller;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ingsw.dietiDeals24.R;
import com.ingsw.dietiDeals24.controller.formstate.DownwardAuctionAttributesFormState;
import com.ingsw.dietiDeals24.controller.formstate.ReverseAuctionAttributesFormState;
import com.ingsw.dietiDeals24.controller.formstate.SilentAuctionAttributesFormState;
import com.ingsw.dietiDeals24.exceptions.AuthenticationException;
import com.ingsw.dietiDeals24.exceptions.ConnectionException;
import com.ingsw.dietiDeals24.model.DownwardAuction;
import com.ingsw.dietiDeals24.model.ReverseAuction;
import com.ingsw.dietiDeals24.model.SilentAuction;
import com.ingsw.dietiDeals24.network.RetroFitHolder;
import com.ingsw.dietiDeals24.network.TokenHolder;
import com.ingsw.dietiDeals24.network.dao.CreateAuctionDao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;

import retrofit2.Response;

public class CreateAuctionController implements RetroFitHolder {
    private static MutableLiveData<ReverseAuctionAttributesFormState> reverseAuctionFormState = new MutableLiveData<>();
    private static MutableLiveData<SilentAuctionAttributesFormState> silentAuctionFormState = new MutableLiveData<>();
    private static MutableLiveData<DownwardAuctionAttributesFormState> downwardAuctionFormState = new MutableLiveData<>();

    public static MutableLiveData<DownwardAuctionAttributesFormState> getDownwardAuctionFormState() {
        return downwardAuctionFormState;
    }

    public static void downwardAuctionInputChanged(String initialPrice, String minimalPrice,
                                                   String decrementAmount,
                                                   String months, String days,
                                                   String hours, String minutes, Context context) {
        if (!isInitialPriceValid(initialPrice)) {
            downwardAuctionFormState.setValue(new DownwardAuctionAttributesFormState(context.getString(R.string.price_error), null, null, null));
        } else if (!isMinimalPriceValid(minimalPrice, initialPrice)) {
            downwardAuctionFormState.setValue(new DownwardAuctionAttributesFormState(null, context.getString(R.string.price_error), null, null));
        } else if (!isDecrementAmountValid(decrementAmount, initialPrice, minimalPrice, months, days, hours, minutes)) {
            downwardAuctionFormState.setValue(new DownwardAuctionAttributesFormState(null, null, context.getString(R.string.downward_auction_decrement_amount_error), null));
        } else {
            downwardAuctionFormState.setValue(new DownwardAuctionAttributesFormState(true));
        }
    }


    public static MutableLiveData<SilentAuctionAttributesFormState> getSilentAuctionFormState() {
        return silentAuctionFormState;
    }

    public static void silentAuctionInputChanged(boolean isExpirationDateSelected, Context context) {
        if (!isExpirationDateSelected) {
            silentAuctionFormState.setValue(new SilentAuctionAttributesFormState(context.getString(R.string.expiration_date_error)));
        } else {
            silentAuctionFormState.setValue(new SilentAuctionAttributesFormState(true));
        }
    }

    public static MutableLiveData<ReverseAuctionAttributesFormState> getReverseAuctionFormState() {
        return reverseAuctionFormState;
    }

    public static void reverseAuctionInputChanged(String initialPrice, boolean isExpirationDateSelected, Context context) {
        if (!isInitialPriceValid(initialPrice)) {
            reverseAuctionFormState.setValue(new ReverseAuctionAttributesFormState(context.getString(R.string.price_error), null));
        } else if (!isExpirationDateSelected) {
            reverseAuctionFormState.setValue(new ReverseAuctionAttributesFormState(null, context.getString(R.string.expiration_date_error)));
        } else {
            reverseAuctionFormState.setValue(new ReverseAuctionAttributesFormState(true));
        }
    }

    private static boolean isInitialPriceValid(String initialPrice) {
        if (initialPrice == null) {
            return false;
        }
        if (initialPrice.isEmpty()) {
            return false;
        }
        return initialPrice.length() <= 10;
    }

    private static boolean isDecrementAmountValid(String decrementAmountString,
                                                  String initialPriceString,
                                                  String secretMinimalPriceString,
                                                  String monthsString,
                                                  String daysString,
                                                  String hoursString,
                                                  String minutesString) {

        if (decrementAmountString == null || initialPriceString == null ||
                secretMinimalPriceString == null || monthsString == null ||
                daysString == null || hoursString == null || minutesString == null) {
            return false;
        }
        if (decrementAmountString.isEmpty() || initialPriceString.isEmpty() ||
                secretMinimalPriceString.isEmpty() || monthsString.isEmpty() ||
                daysString.isEmpty() || hoursString.isEmpty() || minutesString.isEmpty()) {
            return false;
        }

        double initialPrice = Double.parseDouble(initialPriceString);
        double decrementAmount = Double.parseDouble(decrementAmountString);
        double secretMinimalPrice = Double.parseDouble(secretMinimalPriceString);
        long months = Long.parseLong(monthsString);
        long days = Long.parseLong(daysString);
        long hours = Long.parseLong(hoursString);
        long minutes = Long.parseLong(minutesString);

        double totalTimeInMinutes = (((initialPrice - secretMinimalPrice) / decrementAmount) *
                getDecrementTime(months, days, hours, minutes)) / 60.0;

        if (decrementAmount > initialPrice * 0.15) {
            return false;
        }
        return totalTimeInMinutes >= 30;
    }

    public static long getDecrementTime(long months, long days, long hours, long minutes) {
        long totalSeconds = 0;
        totalSeconds += months * 30 * 24 * 60 * 60;
        totalSeconds += days * 24 * 60 * 60;
        totalSeconds += hours * 60 * 60;
        totalSeconds += minutes * 60L;
        return totalSeconds;
    }

    public static String calculateNextDecrement(long months, long days, long hours, long minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) CreateAuctionController.getDecrementTime(months, days, hours, minutes));
        Timestamp nextDecrementDate = new Timestamp(calendar.getTimeInMillis());

        return formatTimestamp(nextDecrementDate.toString());
    }

    public static String formatTimestamp(String timestamp) {
        String[] parts = timestamp.split(" ");
        String datePart = parts[0];
        String timePart = parts[1].substring(0, 8);

        String[] dateParts = datePart.split("-");
        String formattedDate = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];

        return formattedDate + " " + timePart;
    }

    private static boolean isMinimalPriceValid(String minimalPrice, String initialPrice) {
        if (minimalPrice == null || initialPrice == null) {
            return false;
        }
        if (minimalPrice.isEmpty()) {
            return false;
        }
        if (Double.parseDouble(minimalPrice) > Double.parseDouble(initialPrice)) {
            return false;
        }
        return minimalPrice.length() <= 10;
    }

    private CreateAuctionController() {
    }

    public static CompletableFuture<Boolean> createAuction(ReverseAuction newAuction) throws ConnectionException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<ReverseAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyAuctionsController.setUpdatedReverse(false);
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

    public static CompletableFuture<Boolean> createAuction(SilentAuction newAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<SilentAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyAuctionsController.setUpdatedSilent(false);
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

    public static CompletableFuture<Boolean> createAuction(DownwardAuction newAuction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateAuctionDao createAuctionDao = retrofit.create(CreateAuctionDao.class);
                Response<DownwardAuction> response = createAuctionDao.createAuction(newAuction, TokenHolder.getAuthToken()).execute();

                if (response.isSuccessful()) {
                    MyAuctionsController.setUpdatedDownward(false);
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

    public static boolean isValidDecrementAmount(double initialPrice, double decrementAmount) {
        return decrementAmount <= initialPrice * 0.15;
    }
}
