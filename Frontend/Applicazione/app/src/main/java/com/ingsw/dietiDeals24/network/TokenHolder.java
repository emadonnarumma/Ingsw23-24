package com.ingsw.dietiDeals24.network;

public class TokenHolder {

    private String token;
    private static TokenHolder instance;

    private TokenHolder() {
    }

    public static TokenHolder getInstance() {
        if (instance == null) {
            instance = new TokenHolder();
        }
        return instance;
    }

    public static String getAuthToken() {
        return "Bearer " + TokenHolder.instance.token;
    }

    public void setToken(String token) {
        TokenHolder.instance.token = token;
    }


    public String getToken() {
        return token;
    }
}
