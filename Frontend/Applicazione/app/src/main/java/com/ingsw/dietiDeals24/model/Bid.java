package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.BidStatus;

import java.sql.Timestamp;

public class Bid {
    private Integer id;
    private Double moneyAmount;
    private BidStatus status;
    private Timestamp timestamp;

    public Bid(Double moneyAmount, BidStatus status, Timestamp timestamp) {
        this.moneyAmount = moneyAmount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
