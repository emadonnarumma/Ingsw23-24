package com.ingsw.dietiDeals24.model.enumeration;

public enum Role {
    BUYER,
    SELLER;

    public static String toItalianString(Role role) {
        if (role == BUYER) {
            return "Compratore";
        } else {
            return "Venditore";
        }
    }
}
