package com.ingsw.dietiDeals24.controller.formstate;

public class LoginFormState {
    private String emailError;
    private String passwordError;
    private boolean isDataValid;

    public LoginFormState(String emailError, String passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    public LoginFormState(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    public String getEmailError() {
        return emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}