package com.ingsw.dietiDeals24.model;

public class BankAccount {
    private Integer id;
    private String iban;
    private String iva;

    public BankAccount(String iban, String iva) {
        this.iban = iban;
        this.iva = iva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
