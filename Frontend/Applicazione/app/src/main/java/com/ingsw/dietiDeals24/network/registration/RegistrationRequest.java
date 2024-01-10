package com.ingsw.dietiDeals24.network.registration;

import com.ingsw.dietiDeals24.enumeration.Region;

public class RegistrationRequest {
    private String userName;
    private String bio;
    private String role;
    private String email;
    private String password;
    private Region region;


    public RegistrationRequest(String userName, String email, String password, String bio, String region) {
        this.role = "BUYER";
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.region = Region.valueOf(region);
        this.bio = bio;
    }
}
