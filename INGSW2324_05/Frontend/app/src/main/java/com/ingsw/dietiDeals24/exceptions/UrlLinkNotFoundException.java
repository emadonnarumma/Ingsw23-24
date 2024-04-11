package com.ingsw.dietiDeals24.exceptions;

public class UrlLinkNotFoundException extends RuntimeException {
    public UrlLinkNotFoundException() {
        super("Il link fornito è inesistente");
    }

    public UrlLinkNotFoundException(String message) {
        super(message);
    }
}
