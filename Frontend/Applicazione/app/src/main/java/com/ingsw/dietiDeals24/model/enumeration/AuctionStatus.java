package com.ingsw.dietiDeals24.model.enumeration;

public enum AuctionStatus {
    SUCCESSFUL,
    IN_PROGRESS,
    FAILED;

    public static String toItalianString(AuctionStatus status) {
        switch (status) {
            case SUCCESSFUL:
                return "VENDUTA";
            case IN_PROGRESS:
                return "IN CORSO";
            case FAILED:
                return "FALLITA";
            default:
                return null;
        }
    }
}
