package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.Region;

public class User {

    private String name;
    private String email;
    private String password;
    private String bio;
    private Region region;

    public User(String name, String email, String password, String bio, Region region) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.region = region;
    }
}
