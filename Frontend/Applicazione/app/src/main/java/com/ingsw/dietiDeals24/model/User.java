package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.Region;
import com.ingsw.dietiDeals24.enumeration.Role;

public class User {
    private Role role;
    private String name;
    private String email;
    private String password;
    private String bio;
    private Region region;


    public User(Role role, String name, String email, String password, String bio, Region region) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.region = region;
    }



    public Role getRole() {
        return role;
    }


    public void setRole(Role role) {
        this.role = role;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getBio() {
        return bio;
    }


    public void setBio(String bio) {
        this.bio = bio;
    }


    public Region getRegion() {
        return region;
    }


    public void setRegion(Region region) {
        this.region = region;
    }
}
