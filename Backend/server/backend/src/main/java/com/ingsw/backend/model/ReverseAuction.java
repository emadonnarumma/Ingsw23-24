package com.ingsw.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="reverse_auctions")
public class ReverseAuction extends Auction {

    @Column(nullable = false)
    private Double startingPrice;

    @Column(nullable = false)
    private Timestamp expirationDate;

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private Buyer owner;


}
