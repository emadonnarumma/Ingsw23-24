package com.ingsw.dietiDeals24.controller.formstate;

public class RegistrationFormState {
    private String usernameError;
    private String emailError;
    private String passwordError;
    private String repeatPasswordError;
    private boolean isDataValid;

    public RegistrationFormState(String usernameError, String emailError, String passwordError, String repeatPasswordError) {
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.repeatPasswordError = repeatPasswordError;
        this.isDataValid = false;
    }

    public RegistrationFormState(boolean isDataValid) {
        this.usernameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.repeatPasswordError = null;
        this.isDataValid = isDataValid;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setRepeatPasswordError(String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }

    public boolean isUsernameValid() {
        return usernameError == null;
    }

    public boolean isEmailValid() {
        return emailError == null;
    }

    public boolean isPasswordValid() {
        return passwordError == null;
    }

    public boolean isRepeatPasswordValid() {
        return repeatPasswordError == null;
    }

    public boolean isAllValid() {
        return isUsernameValid() && isEmailValid() && isPasswordValid() && isRepeatPasswordValid();
    }
}
