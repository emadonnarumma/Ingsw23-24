package com.ingsw.backend.model;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("REVERSE")
public class ReverseBid extends Bid {

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    @JoinColumn(name = "owner_role", referencedColumnName = "role")
    @ToString.Exclude
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "idAuction")
    @ToString.Exclude
    private ReverseAuction reverseAuction;

}
