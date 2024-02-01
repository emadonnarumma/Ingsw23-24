package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.AuctionStatus;
import com.ingsw.dietiDeals24.enumeration.AuctionType;
import com.ingsw.dietiDeals24.enumeration.Category;
import com.ingsw.dietiDeals24.enumeration.Wear;

import java.sql.Timestamp;
import java.util.List;

public class SilentAuction extends Auction {

    private Timestamp expirationDate;
    private Long withdrawalTime;
    private List<SilentBid> receivedBids;


    public SilentAuction(User owner, String title, String description,
                         Wear wear, Category category, AuctionStatus status,
                         Timestamp expirationDate, Long withdrawalTime, List<SilentBid> receivedBids) {

        super(owner, title, description, wear, category, status, AuctionType.SILENT);
        this.expirationDate = expirationDate;
        this.withdrawalTime = withdrawalTime;
        this.receivedBids = receivedBids;
    }

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

    public List<SilentBid> getReceivedBids() {
        return receivedBids;
    }

    public void setReceivedBids(List<SilentBid> receivedBids) {
        this.receivedBids = receivedBids;
    }
}
