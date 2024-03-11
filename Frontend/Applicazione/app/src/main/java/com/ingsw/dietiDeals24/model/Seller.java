package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;

import java.util.List;

public class Seller extends User {

    private BankAccount bankAccount;

    public Seller(Role role, String name, String email, String password, String bio,
                  Region region, List<ExternalLink> externalLinks, BankAccount bankAccount) {

        super(role, name, email, password, bio, region, externalLinks);
        this.bankAccount = bankAccount;
    }

    public Seller(Seller body) {
        super(body);
        this.bankAccount = body.bankAccount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
