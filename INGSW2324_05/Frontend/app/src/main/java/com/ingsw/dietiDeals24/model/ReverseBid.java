package com.ingsw.dietiDeals24.model;


import com.ingsw.dietiDeals24.model.enumeration.BidStatus;

public class ReverseBid extends Bid {

    private final String type =  "REVERSE";
    private Seller seller;
    private ReverseAuction reverseAuction;

    public ReverseBid(Double moneyAmount, BidStatus status, String timestamp,
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
