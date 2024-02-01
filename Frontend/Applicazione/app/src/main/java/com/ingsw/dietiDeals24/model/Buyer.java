package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.enumeration.Region;
import com.ingsw.dietiDeals24.enumeration.Role;

import java.util.List;

public class Buyer extends User {

    List<ReverseAuction> reverseAuctions;

    public Buyer(Role role, String name, String email, String password, String bio, Region region,
                 List<ExternalLink> externalLinks, List<ReverseAuction> reverseAuctions) {

        super(role, name, email, password, bio, region, externalLinks);
        this.reverseAuctions = reverseAuctions;
    }

    public List<ReverseAuction> getReverseAuctions() {
        return reverseAuctions;
    }

    public void setReverseAuctions(List<ReverseAuction> reverseAuctions) {
        this.reverseAuctions = reverseAuctions;
    }
}
