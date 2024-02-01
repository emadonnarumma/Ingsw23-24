package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.Region;
import com.ingsw.dietiDeals24.enumeration.Role;

import java.util.List;

public class Seller extends User {

    private BankAccount bankAccount;
    private List<SilentAuction> silentAucions;
    private List<DownwardAuction> downwardAuctions;
    private List<ReverseBid> bids;

    public Seller(Role role, String name, String email, String password, String bio,
                  Region region, List<ExternalLink> externalLinks, BankAccount bankAccount,
                  List<SilentAuction> silentAucions, List<DownwardAuction> downwardAuctions,
                  List<ReverseBid> bids) {

        super(role, name, email, password, bio, region, externalLinks);
        this.bankAccount = bankAccount;
        this.silentAucions = silentAucions;
        this.downwardAuctions = downwardAuctions;
        this.bids = bids;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public List<SilentAuction> getSilentAucions() {
        return silentAucions;
    }

    public void setSilentAucions(List<SilentAuction> silentAucions) {
        this.silentAucions = silentAucions;
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
