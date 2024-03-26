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
@DiscriminatorValue("SILENT")
public class SilentBid extends Bid {

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    @JoinColumn(name = "owner_role", referencedColumnName = "role")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "idAuction")
    private SilentAuction silentAuction;


}
