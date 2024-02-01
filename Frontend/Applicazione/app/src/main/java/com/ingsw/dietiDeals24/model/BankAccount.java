package com.ingsw.dietiDeals24.model;

public class BankAccount {
    private Integer id;
    private Seller seller;
    private String iban;
    private String iva;

    public BankAccount(Seller seller, String iban, String iva) {
        this.seller = seller;
        this.iban = iban;
        this.iva = iva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
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
