package com.ingsw.dietiDeals24.controller.formstate;

public class ReverseBidFormState {
    private String bidError;
    private boolean isDataValid;

    public ReverseBidFormState(String bidError) {
        this.bidError = bidError;
        this.isDataValid = false;
    }

    public ReverseBidFormState(boolean isDataValid) {
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

    public boolean isBidValid() {
        return bidError == null;
    }
}