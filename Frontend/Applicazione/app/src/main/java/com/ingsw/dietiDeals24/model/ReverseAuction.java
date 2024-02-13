package com.ingsw.dietiDeals24.model;


import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;

public class ReverseAuction extends Auction {

    private double currentPrice;
    private String expirationDate;

    public ReverseAuction(User owner, String title, String description,
                          Wear wear, Category category, AuctionStatus status,
                          double currentPrice, String expirationDate) {

        super(owner, title, description, wear, category, status, AuctionType.REVERSE);
        this.currentPrice = currentPrice;
        this.expirationDate = expirationDate;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
