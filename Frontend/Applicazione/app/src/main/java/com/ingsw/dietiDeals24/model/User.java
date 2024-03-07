package com.ingsw.dietiDeals24.model;

import androidx.annotation.Nullable;

import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private Role role;
    private String name;
    private String email;
    private String password;
    private String bio;
    private Region region;
    private List<ExternalLink> externalLinks;

    public User() {
    }

    public User(Role role, String name, String email, String password, String bio, Region region, List<ExternalLink> externalLinks) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.region = region;
        this.externalLinks = externalLinks;
    }

    /**
     * Copy constructor
     */
    public User(User user) {
        if (user == null) {
            return;
        }
        this.role = user.role;
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
        this.bio = user.bio;
        this.region = user.region;
        for (ExternalLink link : user.externalLinks) {
            this.externalLinks.add(new ExternalLink(link));
        }
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

    public List<ExternalLink> getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(List<ExternalLink> externalLinks) {
        this.externalLinks = externalLinks;
    }

    public boolean isSeller() {
        return role == Role.SELLER;
    }

    public boolean hasExternalLinks() {
        return !externalLinks.isEmpty();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof User))
            return false;

        User user = (User) obj;
        return user.getEmail().equals(email);
    }
}
