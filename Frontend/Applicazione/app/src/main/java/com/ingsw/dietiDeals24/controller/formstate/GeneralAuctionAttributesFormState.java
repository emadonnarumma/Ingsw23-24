package com.ingsw.dietiDeals24.controller.formstate;

public class GeneralAuctionAttributesFormState {
    private String titleError;
    private String descriptionError;
    private String wearError;
    private String categoryError;
    private boolean isDataValid;

    public GeneralAuctionAttributesFormState(String titleError, String descriptionError, String wearError, String categoryError) {
        this.titleError = titleError;
        this.descriptionError = descriptionError;
        this.wearError = wearError;
        this.categoryError = categoryError;
        this.isDataValid = false;
    }

    public GeneralAuctionAttributesFormState(boolean isDataValid) {
        this.titleError = null;
        this.descriptionError = null;
        this.wearError = null;
        this.categoryError = null;
        this.isDataValid = isDataValid;
    }

    public String getTitleError() {
        return titleError;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public String getWearError() {
        return wearError;
    }

    public String getCategoryError() {
        return categoryError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    public void setTitleError(String titleError) {
        this.titleError = titleError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public void setWearError(String wearError) {
        this.wearError = wearError;
    }

    public void setCategoryError(String categoryError) {
        this.categoryError = categoryError;
    }

    public void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }
}