package com.ingsw.dietiDeals24.model;

public class Notification {

    private Integer idNotification;
    private String message;

    public Notification(Integer idNotification, String message) {
        this.idNotification = idNotification;
        this.message = message;
    }

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
