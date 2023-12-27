package com.ingsw.backend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="downward_auctions")
public class DownwardAuction extends Auction {

    @Column(nullable = false)
    private Double secretMinimumPrice;

    @Column(nullable = false)
    private Double currentPrice;

    @Column(nullable = false)
    private Double decrementAmount;

    @Column(nullable = false)
    private Long decrementTime;

    @Column(nullable = false)
    private Timestamp nextDecrement;

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private Seller owner;









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

    public void setDecrementTime(Long decremntTime) {
        this.decrementTime = decremntTime;
    }

    public Timestamp getNextDecrement() {
        return nextDecrement;
    }

    public void setNextDecrement(Timestamp nextDecrement) {
        this.nextDecrement = nextDecrement;
    }
}
