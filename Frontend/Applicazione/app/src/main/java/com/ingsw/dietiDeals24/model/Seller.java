package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.Region;
import com.ingsw.dietiDeals24.enumeration.Role;

import java.util.List;

public class Seller extends User {

    private BankAccount bankAccount;
    private List<SilentAuction> silentAuctions;
    private List<DownwardAuction> downwardAuctions;
    private List<ReverseBid> bids;

    public Seller(Role role, String name, String email, String password, String bio,
                  Region region, List<ExternalLink> externalLinks, BankAccount bankAccount,
                  List<SilentAuction> silentAuctions, List<DownwardAuction> downwardAuctions,
                  List<ReverseBid> bids) {

        super(role, name, email, password, bio, region, externalLinks);
        this.bankAccount = bankAccount;
        this.silentAuctions = silentAuctions;
        this.downwardAuctions = downwardAuctions;
        this.bids = bids;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

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

    public List<ReverseBid> getBids() {
        return bids;
    }

    public void setBids(List<ReverseBid> bids) {
        this.bids = bids;
    }
}
