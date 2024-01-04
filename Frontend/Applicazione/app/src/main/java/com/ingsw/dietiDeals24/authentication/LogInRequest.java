package com.ingsw.dietiDeals24.authentication;

public class LogInRequest {
    public String email;
    public String password;

    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
