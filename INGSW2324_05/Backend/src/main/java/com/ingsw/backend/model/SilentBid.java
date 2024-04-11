package com.ingsw.backend.model;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("SILENT")
public class SilentBid extends Bid {

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    @JoinColumn(name = "owner_role", referencedColumnName = "role")
    @ToString.Exclude
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "idAuction")
    @ToString.Exclude
    private SilentAuction silentAuction;
}
