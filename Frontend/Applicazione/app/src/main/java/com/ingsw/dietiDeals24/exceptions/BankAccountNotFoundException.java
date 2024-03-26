package com.ingsw.dietiDeals24.exceptions;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException() {
        super("Il conto corrente non è stato trovato");
    }

    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
