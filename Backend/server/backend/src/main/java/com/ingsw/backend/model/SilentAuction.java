package com.ingsw.backend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "silent_auctions")
public class SilentAuction extends Auction {

    @Column(nullable = false)
    private Timestamp expirationDate;

    @Column(nullable = false)
    private Long withdrawalTime;

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private Seller owner;









    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getWithdrawalTime() {
        return withdrawalTime;
    }

    public void setWithdrawalTime(Long withdrawalTime) {
        this.withdrawalTime = withdrawalTime;
    }
}
