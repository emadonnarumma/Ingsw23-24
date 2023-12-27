package com.ingsw.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "silent_bids")
public class SilentBid extends Bid {

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private Buyer owner;










    public Buyer getOwner() {
        return owner;
    }

    public void setOwner(Buyer owner) {
        this.owner = owner;
    }
}
