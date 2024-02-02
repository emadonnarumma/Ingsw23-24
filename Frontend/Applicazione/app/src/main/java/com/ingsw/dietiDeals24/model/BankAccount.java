package com.ingsw.dietiDeals24.model;

public class BankAccount {
    private Integer idBankAccount;
    private Seller seller;
    private String iban;
    private String iva;

    public BankAccount(Seller seller, String iban, String iva) {
        this.seller = seller;
        this.iban = iban;
        this.iva = iva;
    }

    public Integer getIdBankAccount() {
        return idBankAccount;
    }

    public void setIdBankAccount(Integer idBankAccount) {
        this.idBankAccount = idBankAccount;
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
