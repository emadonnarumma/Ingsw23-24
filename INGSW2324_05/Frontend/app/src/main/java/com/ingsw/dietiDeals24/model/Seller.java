package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;

import java.util.List;

public class Seller extends User {

    private BankAccount bankAccount;

    public Seller(String name, String email, String password, String bio,
                  Region region, List<ExternalLink> externalLinks, BankAccount bankAccount) {

        super(Role.SELLER, name, email, password, bio, region, externalLinks);
        this.bankAccount = bankAccount;
    }

    public Seller(User user, BankAccount bankAccount) {
        super(Role.SELLER, user.getName(), user.getEmail(), user.getPassword(), user.getBio(), user.getRegion(), user.getExternalLinks());
        this.bankAccount = bankAccount;
    }

    public Seller(Seller body) {
        super(body);
        this.bankAccount = body.bankAccount;
    }

    public Seller(User body) {
        super(body);
        bankAccount = null;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
