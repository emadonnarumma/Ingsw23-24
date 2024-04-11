package com.ingsw.dietiDeals24.controller.formstate;

public class BankAccountFormState {
    private String ibanError;
    private String ivaError;
    private boolean dataValid;

    public BankAccountFormState(String ibanError, String ivaError) {
        this.ibanError = ibanError;
        this.ivaError = ivaError;
        this.dataValid = false;
    }

    public BankAccountFormState(boolean dataValid) {
        this.ibanError = null;
        this.ivaError = null;
        this.dataValid = dataValid;
    }

    public String getIbanError() {
        return ibanError;
    }

    public String getIvaError() {
        return ivaError;
    }

    public boolean isDataValid() {
        return dataValid;
    }
}