package com.ingsw.backend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="reverse_auctions")
public class ReverseAuction extends Auction {

    @Column(nullable = false)
    private Double startingPrice;

    @Column(nullable = false)
    private Timestamp expirationDate;

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private Buyer owner;








    public Double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }
}
