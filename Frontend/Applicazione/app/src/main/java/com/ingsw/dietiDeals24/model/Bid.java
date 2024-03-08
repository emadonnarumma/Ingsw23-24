package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.BidStatus;

import java.sql.Timestamp;

public class Bid {
    private Integer idBid;
    private Double moneyAmount;
    private BidStatus status;
    private String timestamp;

    public Bid(Double moneyAmount, BidStatus status, String timestamp) {
        this.moneyAmount = moneyAmount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Integer getIdBid() {
        return idBid;
    }

    public void setIdBid(Integer idBid) {
        this.idBid = idBid;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getPrice() {
        return moneyAmount;
    }
}
