package com.ingsw.dietiDeals24.controller.formstate;

public class SilentBidFormState {
    private String bidError;
    private boolean isDataValid;

    public SilentBidFormState(String bidError) {
        this.bidError = bidError;
        this.isDataValid = false;
    }

    public SilentBidFormState(boolean isDataValid) {
        this.bidError = null;
        this.isDataValid = isDataValid;
    }

    public String getBidError() {
        return bidError;
    }

    public void setBidError(String bidError) {
        this.bidError = bidError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }

    public boolean isBidValid() {
        return bidError == null;
    }
}