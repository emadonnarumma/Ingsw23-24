package com.ingsw.dietiDeals24.controller.formstate;

public class MakePaymentFormState {
    private String cardCodeError;
    private String nameError;
    private String surnameError;
    private String expirationDateError;
    private String cvvError;
    private boolean isDataValid;

    public MakePaymentFormState(String cardCodeError, String nameError, String surnameError, String expirationDateError, String cvvError) {
        this.cardCodeError = cardCodeError;
        this.nameError = nameError;
        this.surnameError = surnameError;
        this.expirationDateError = expirationDateError;
        this.cvvError = cvvError;
        this.isDataValid = false;
    }

    public MakePaymentFormState(boolean isDataValid) {
        this.cardCodeError = null;
        this.nameError = null;
        this.surnameError = null;
        this.expirationDateError = null;
        this.cvvError = null;
        this.isDataValid = isDataValid;
    }

    public String getCardCodeError() {
        return cardCodeError;
    }

    public void setCardCodeError(String cardCodeError) {
        this.cardCodeError = cardCodeError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getSurnameError() {
        return surnameError;
    }

    public void setSurnameError(String surnameError) {
        this.surnameError = surnameError;
    }

    public String getExpirationDateError() {
        return expirationDateError;
    }

    public void setExpirationDateError(String expirationDateError) {
        this.expirationDateError = expirationDateError;
    }

    public String getCvvError() {
        return cvvError;
    }

    public void setCvvError(String cvvError) {
        this.cvvError = cvvError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }
}