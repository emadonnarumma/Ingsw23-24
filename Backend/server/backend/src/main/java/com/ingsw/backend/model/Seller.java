package com.ingsw.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="sellers")
public class Seller extends User {

    @OneToOne(mappedBy = "seller")
    private BankAccount bankAccount;











    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
