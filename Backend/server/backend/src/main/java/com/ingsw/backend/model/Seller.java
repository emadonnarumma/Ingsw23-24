package com.ingsw.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="sellers")
public class Seller extends User {

    @OneToOne(mappedBy = "seller")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<SilentAuction> silentAuctions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<DownwardAuction> downwardAuctions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<ReverseBid> bids;








    public List<SilentAuction> getSilentAuctions() {
        return silentAuctions;
    }

    public void setSilentAuctions(List<SilentAuction> silentAuctions) {
        this.silentAuctions = silentAuctions;
    }

    public List<DownwardAuction> getDownwardAuctions() {
        return downwardAuctions;
    }

    public void setDownwardAuctions(List<DownwardAuction> downwardAuctions) {
        this.downwardAuctions = downwardAuctions;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
