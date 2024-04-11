package com.ingsw.dietiDeals24.model;


import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;

import java.io.Serializable;

public class ReverseAuction extends Auction implements Serializable {

    private double startingPrice;
    private String expirationDate;

    public ReverseAuction(User owner, String title, String description,
                          Wear wear, Category category, AuctionStatus status,
                          double startingPrice, String expirationDate) {

        super(owner, title, description, wear, category, status, AuctionType.REVERSE);
        this.startingPrice = startingPrice;
        this.expirationDate = expirationDate;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
