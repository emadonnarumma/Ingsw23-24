package com.ingsw.dietiDeals24.controller.formstate;

public class SilentAuctionAttributesFormState {
    private String expirationDateError;
    private boolean isDataValid;

    public SilentAuctionAttributesFormState(String expirationDateError) {
        this.expirationDateError = expirationDateError;
        this.isDataValid = false;
    }

    public SilentAuctionAttributesFormState(boolean isDataValid) {
        this.expirationDateError = null;
        this.isDataValid = isDataValid;
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