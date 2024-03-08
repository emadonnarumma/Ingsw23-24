package com.ingsw.dietiDeals24.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {

    private Role role;
    private String name;
    private String email;
    private String password;
    @Nullable
    private String bio;
    private Region region;
    private List<ExternalLink> externalLinks;

    public User() {
        this.role = Role.BUYER;
        this.region = Region.NOT_SPECIFIED;
        this.externalLinks = new LinkedList<>();
    }

    public User(Role role, String name, String email, String password, @Nullable String bio, @Nullable Region region, @Nullable List<ExternalLink> externalLinks) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;

        if (region == null)
            this.region = Region.NOT_SPECIFIED;
        else
            this.region = region;

        if (externalLinks == null)
            this.externalLinks = new LinkedList<>();
        else
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

        if (user.region == null)
            this.region = Region.NOT_SPECIFIED;
        else
            this.region = user.region;

        if(user.externalLinks == null)
            this.externalLinks = new LinkedList<>();
        else
            this.externalLinks = new LinkedList<>(user.externalLinks);
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

    @Nullable
    public String getBio() {
        return bio;
    }

    public void setBio(@Nullable String bio) {
        this.bio = bio;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(@Nullable Region region) {
        if(region == null)
            this.region = Region.NOT_SPECIFIED;
        else
            this.region = region;
    }

    public List<ExternalLink> getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(@Nullable List<ExternalLink> externalLinks) {
        if (externalLinks == null)
            this.externalLinks = new LinkedList<>();
        else
            this.externalLinks = externalLinks;
    }

    public boolean isSeller() {
        return role == Role.SELLER;
    }

    public boolean hasExternalLinks() {
        if(externalLinks == null)
            return false;
        return !externalLinks.isEmpty();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof User))
            return false;

        User user = (User) obj;
        return user.email.equals(email) && user.role.equals(role);
    }
}
