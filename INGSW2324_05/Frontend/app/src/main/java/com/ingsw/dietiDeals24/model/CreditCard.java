package com.ingsw.dietiDeals24.model;

public class CreditCard {
    private String cardNumber;
    private String cardOwnerName;
    private String cardOwnerSurname;
    private String cvv;
    private String expirationDate;

    public CreditCard(String cardNumber, String cardOwnerName, String cardOwnerSurname, String cvv, String expirationDate) {
        this.cardNumber = cardNumber;
        this.cardOwnerName = cardOwnerName;
        this.cardOwnerSurname = cardOwnerSurname;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardOwnerName() {
        return cardOwnerName;
    }

    public void setCardOwnerName(String cardOwnerName) {
        this.cardOwnerName = cardOwnerName;
    }

    public String getCardOwnerSurname() {
        return cardOwnerSurname;
    }

    public void setCardOwnerSurname(String cardOwnerSurname) {
        this.cardOwnerSurname = cardOwnerSurname;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
