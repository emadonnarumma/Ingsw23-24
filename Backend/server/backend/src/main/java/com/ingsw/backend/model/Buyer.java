package com.ingsw.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends User {

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<ReverseAuction> reverseAuctions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<SilentBid> bids;

}
