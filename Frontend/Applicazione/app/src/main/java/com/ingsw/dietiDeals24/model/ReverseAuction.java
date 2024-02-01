package com.ingsw.dietiDeals24.model;


import com.ingsw.dietiDeals24.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.enumeration.AuctionType;
import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;

import java.util.List;

public class ReverseAuction extends Auction {

    private double initialPrice;
    private String expirationDate;

    public ReverseAuction(User owner, String title, String description,
                          Wear wear, Category category, AuctionStatus status,
                          double initialPrice, String expirationDate) {

        super(owner, title, description, wear, category, status, AuctionType.REVERSE);
        this.initialPrice = initialPrice;
        this.expirationDate = expirationDate;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
