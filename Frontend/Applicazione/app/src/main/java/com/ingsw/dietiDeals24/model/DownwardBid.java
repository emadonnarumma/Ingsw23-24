package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.BidStatus;

import java.sql.Timestamp;

public class DownwardBid extends Bid {

    private final String type = "DOWNWARD";
    private Buyer buyer;
    private DownwardAuction downwardAuction;

    public DownwardBid(Double moneyAmount, BidStatus status, String timestamp, Buyer buyer,
                       DownwardAuction downwardAuction) {
        super(moneyAmount, status, timestamp);
        this.buyer = buyer;
        this.downwardAuction = downwardAuction;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public DownwardAuction getDownwardAuction() {
        return downwardAuction;
    }

    public void setDownwardAuction(DownwardAuction downwardAuction) {
        this.downwardAuction = downwardAuction;
    }
}
