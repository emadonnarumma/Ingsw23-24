package com.ingsw.dietiDeals24.model;

public class Notification {

    private Integer idNotification;
    private String message;
    private User user;
    private DownwardAuction auction;

    public Notification(Integer idNotification, String message, DownwardAuction auction, User user) {
        this.idNotification = idNotification;
        this.message = message;
        this.auction = auction;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DownwardAuction getAuction() {
        return auction;
    }

    public void setAuction(DownwardAuction auction) {
        this.auction = auction;
    }

}
