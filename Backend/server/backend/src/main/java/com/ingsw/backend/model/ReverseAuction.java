package com.ingsw.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("REVERSE")
public class ReverseAuction extends Auction {

    private Double startingPrice;

    private Timestamp expirationDate;
    
    @OneToMany(mappedBy = "reverseAuction", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ReverseBid> receivedBids;


}
