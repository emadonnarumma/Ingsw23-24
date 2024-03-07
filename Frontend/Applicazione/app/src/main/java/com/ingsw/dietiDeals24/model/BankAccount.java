package com.ingsw.dietiDeals24.model;

public class BankAccount {
    private Integer idBankAccount;
    private String iban;
    private String iva;

    public BankAccount(String iban, String iva) {
        this.iban = iban;
        this.iva = iva;
    }

    public Integer getId() {
        return idBankAccount;
    }

    public void setId(Integer id) {
        this.idBankAccount = id;
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
