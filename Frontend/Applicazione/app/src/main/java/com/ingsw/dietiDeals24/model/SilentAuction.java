package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.model.enumeration.AuctionType;
import com.ingsw.dietiDeals24.model.enumeration.Category;
import com.ingsw.dietiDeals24.model.enumeration.Wear;

import java.io.Serializable;
import java.util.List;

public class SilentAuction extends Auction {

    private String expirationDate;
    private Long withdrawalTime;


    public SilentAuction(User owner, String title, String description,
                         Wear wear, Category category, AuctionStatus status,
                         String expirationDate, Long withdrawalTime) {

        super(owner, title, description, wear, category, status, AuctionType.SILENT);
        this.expirationDate = expirationDate;
        this.withdrawalTime = withdrawalTime;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getWithdrawalTime() {
        return withdrawalTime;
    }

    public void setWithdrawalTime(Long withdrawalTime) {
        this.withdrawalTime = withdrawalTime;
    }
}
