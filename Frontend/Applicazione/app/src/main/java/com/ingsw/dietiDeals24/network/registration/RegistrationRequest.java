package com.ingsw.dietiDeals24.network.registration;

import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;

public class RegistrationRequest {
    private String name;
    private String bio;
    private Role role;
    private String email;
    private String password;
    private Region region;


    public RegistrationRequest(String name, String email, String password, String bio, Region region) {
        this.role = Role.SELLER;
        this.email = email;
        this.password = password;
        this.name = name;
        this.region = region;
        this.bio = bio;
    }
}
