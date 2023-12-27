package com.ingsw.backend.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="bank_accounts")
public class BankAccount {

    @OneToOne
    @JoinColumn(name = "seller_email", referencedColumnName = "email")
    private Seller seller;

    @Id
    @Length(min=27, max=27)
    private String iban;

    @Length(min=11, max=11)
    private String iva;










    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
