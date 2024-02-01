package com.ingsw.dietiDeals24.network;

public class TokenHolder {

    public String token;
    public static TokenHolder instance;

    private TokenHolder() {
    }

    public static String getAuthToken() {
        return "Bearer " + TokenHolder.instance.token;
    }
}
