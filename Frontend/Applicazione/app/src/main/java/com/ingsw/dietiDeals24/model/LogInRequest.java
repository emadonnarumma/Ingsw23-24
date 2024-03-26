package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.Role;

public class LogInRequest {
    private final String email;
    private final Role role;
    private final String password;

    public LogInRequest(String email, String password) {
        this.email = email.toLowerCase();
        role = Role.BUYER;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
