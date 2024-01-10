package com.ingsw.dietiDeals24.network.login;

public class LogInRequest {
    private final String email;
    private final String password;

    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
