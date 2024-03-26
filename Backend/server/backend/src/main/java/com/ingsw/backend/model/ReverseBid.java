package com.ingsw.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("REVERSE")
public class ReverseBid extends Bid {

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="owner_email", referencedColumnName="email"),
            @JoinColumn(name="owner_role", referencedColumnName="role")
    })
    private Seller seller;
    
    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "idAuction")
    private ReverseAuction reverseAuction;

}
