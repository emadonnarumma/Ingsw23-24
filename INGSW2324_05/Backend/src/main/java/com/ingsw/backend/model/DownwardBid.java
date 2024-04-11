package com.ingsw.backend.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("DOWNWARD")
public class DownwardBid extends Bid {
    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    @JoinColumn(name = "owner_role", referencedColumnName = "role")
    @ToString.Exclude
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "idAuction")
    @ToString.Exclude
    private DownwardAuction downwardAuction;
}
