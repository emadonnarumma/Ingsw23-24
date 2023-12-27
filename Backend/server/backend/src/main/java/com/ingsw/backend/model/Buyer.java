package com.ingsw.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name="buyers")
public class Buyer extends User {

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<ReverseAuction> reverseAuctions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<SilentBid> bids;










    public List<SilentBid> getBids() {
        return bids;
    }

    public void setBids(List<SilentBid> bids) {
        this.bids = bids;
    }

    public List<ReverseAuction> getReverseAuctions() {
        return reverseAuctions;
    }

    public void setReverseAuctions(List<ReverseAuction> reverseAuctions) {
        this.reverseAuctions = reverseAuctions;
    }
}
