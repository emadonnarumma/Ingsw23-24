package com.ingsw.dietiDeals24.model;

import com.ingsw.dietiDeals24.model.enumeration.Region;
import com.ingsw.dietiDeals24.model.enumeration.Role;

import java.util.List;

public class Buyer extends User {

    public Buyer(String name, String email, String password, String bio, Region region,
                 List<ExternalLink> externalLinks) {

        super(Role.BUYER, name, email, password, bio, region, externalLinks);
    }

    /**
     * Copy constructor
     */
    public Buyer(Buyer buyer) {
        super(buyer);
    }
}
