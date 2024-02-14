package com.ingsw.dietiDeals24.controller.editProfile;

public class ExternalLinkFormState {
    private String titleError;
    private String urlError;
    private boolean isDataValid;

    public ExternalLinkFormState(String titleError, String urlError) {
        this.titleError = titleError;
        this.urlError = urlError;
        this.isDataValid = false;
    }

    public ExternalLinkFormState(boolean isDataValid) {
        this.titleError = null;
        this.urlError = null;
        this.isDataValid = isDataValid;
    }

    public String getTitleError() {
        return titleError;
    }

    public String getUrlError() {
        return urlError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
