package com.ingsw.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@SuppressWarnings("serial")

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends User {

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ReverseAuction> reverseAuctions;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER)
    @JsonManagedReference("buyer-silentBid")
    private List<SilentBid> bids;

}
