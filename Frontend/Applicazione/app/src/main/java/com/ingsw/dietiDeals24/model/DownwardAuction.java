package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;

public class DownwardAuction extends Auction {

    private Double secretMinimumPrice;
    private Double currentPrice;
    private Double decrementAmount;
    private Long decrementTime;
    private String nextDecrement;

    public DownwardAuction(User owner, String title, String description,
                           Wear wear, Category category, AuctionStatus status,
                           Double secretMinimumPrice, Double currentPrice, Double decrementAmount,
                           Long decrementTime, String nextDecrement) {

        super(owner, title, description, wear, category, status, AuctionType.DOWNWARD);
        this.secretMinimumPrice = secretMinimumPrice;
        this.currentPrice = currentPrice;
        this.decrementAmount = decrementAmount;
        this.decrementTime = decrementTime;
        this.nextDecrement = nextDecrement;
    }

    public Double getSecretMinimumPrice() {
        return secretMinimumPrice;
    }

    public void setSecretMinimumPrice(Double secretMinimumPrice) {
        this.secretMinimumPrice = secretMinimumPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getDecrementAmount() {
        return decrementAmount;
    }

    public void setDecrementAmount(Double decrementAmount) {
        this.decrementAmount = decrementAmount;
    }

    public Long getDecrementTime() {
        return decrementTime;
    }

    public void setDecrementTime(Long decrementTime) {
        this.decrementTime = decrementTime;
    }

    public String getNextDecrement() {
        return nextDecrement;
    }

    public void setNextDecrement(String nextDecrement) {
        this.nextDecrement = nextDecrement;
    }
}
