package com.ingsw.dietiDeals24.model.enumeration;

public enum AuctionType {
    REVERSE,
    DOWNWARD,
    SILENT;

    public static String toItalianString(AuctionType auctionType) {
        switch (auctionType) {
            case REVERSE:
                return "Inversa";
            case DOWNWARD:
                return "Al Ribasso";
            case SILENT:
                return "Silenziosa";
            default:
                throw new RuntimeException("AuctionType not found");
        }
    }
}
