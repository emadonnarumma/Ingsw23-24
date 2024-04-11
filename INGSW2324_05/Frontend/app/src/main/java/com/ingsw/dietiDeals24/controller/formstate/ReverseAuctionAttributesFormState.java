package com.ingsw.dietiDeals24.controller.formstate;

public class ReverseAuctionAttributesFormState {
    private String initialPriceError;
    private String expirationDateError;
    private boolean isDataValid;

    public ReverseAuctionAttributesFormState(String initialPriceError, String expirationDateError) {
        this.initialPriceError = initialPriceError;
        this.expirationDateError = expirationDateError;
        this.isDataValid = false;
    }

    public ReverseAuctionAttributesFormState(boolean isDataValid) {
        this.initialPriceError = null;
        this.expirationDateError = null;
        this.isDataValid = isDataValid;
    }

    public String getInitialPriceError() {
        return initialPriceError;
    }

    public void setInitialPriceError(String initialPriceError) {
        this.initialPriceError = initialPriceError;
    }

    public String getExpirationDateError() {
        return expirationDateError;
    }

    public void setExpirationDateError(String expirationDateError) {
        this.expirationDateError = expirationDateError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }
}