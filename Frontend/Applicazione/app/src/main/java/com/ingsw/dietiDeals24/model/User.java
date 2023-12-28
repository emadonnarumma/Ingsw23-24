package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.Region;

public class User {

    public String username;
    private String email;
    private String password;
    private String bio;
    private Region region;

    public User(String username, String email, String password, String bio, Region region) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.region = region;
    }
}
