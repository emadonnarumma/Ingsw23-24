package com.ingsw.dietiDeals24.model;


import com.ingsw.dietiDeals24.enumeration.BidStatus;

import java.sql.Timestamp;

public class ReverseBid extends Bid {

    private Seller seller;
    private ReverseAuction reverseAuction;

    public ReverseBid(Double moneyAmount, BidStatus status, Timestamp timestamp,
                      Seller seller, ReverseAuction reverseAuction) {

        super(moneyAmount, status, timestamp);
        this.seller = seller;
        this.reverseAuction = reverseAuction;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public ReverseAuction getReverseAuction() {
        return reverseAuction;
    }

    public void setReverseAuction(ReverseAuction reverseAuction) {
        this.reverseAuction = reverseAuction;
    }
}
