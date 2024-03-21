package com.ingsw.dietiDeals24.controller.formstate;

public class DownwardAuctionAttributesFormState {
    private String initialPriceError;
    private String minimalPriceError;
    private String decrementAmountError;
    private boolean isDataValid;

    public DownwardAuctionAttributesFormState(String initialPriceError, String minimalPriceError, String decrementAmountError, String decrementTimeError) {
        this.initialPriceError = initialPriceError;
        this.minimalPriceError = minimalPriceError;
        this.decrementAmountError = decrementAmountError;
        this.isDataValid = false;
    }

    public DownwardAuctionAttributesFormState(boolean isDataValid) {
        this.initialPriceError = null;
        this.minimalPriceError = null;
        this.decrementAmountError = null;
        this.isDataValid = isDataValid;
    }

    public String getInitialPriceError() {
        return initialPriceError;
    }

    public void setInitialPriceError(String initialPriceError) {
        this.initialPriceError = initialPriceError;
    }

    public String getMinimalPriceError() {
        return minimalPriceError;
    }

    public void setMinimalPriceError(String minimalPriceError) {
        this.minimalPriceError = minimalPriceError;
    }

    public String getDecrementAmountError() {
        return decrementAmountError;
    }

    public void setDecrementAmountError(String decrementAmountError) {
        this.decrementAmountError = decrementAmountError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }
}