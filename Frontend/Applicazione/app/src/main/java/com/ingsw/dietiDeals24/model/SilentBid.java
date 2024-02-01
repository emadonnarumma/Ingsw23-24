package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.BidStatus;

import java.sql.Timestamp;

public class SilentBid extends Bid {

    private Buyer buyer;
    private SilentAuction silentAuction;

    public SilentBid(Double moneyAmount, BidStatus status, Timestamp timestamp, Buyer buyer,
                     SilentAuction silentAuction) {

        super(moneyAmount, status, timestamp);
        this.buyer = buyer;
        this.silentAuction = silentAuction;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public SilentAuction getSilentAuction() {
        return silentAuction;
    }

    public void setSilentAuction(SilentAuction silentAuction) {
        this.silentAuction = silentAuction;
    }
}
