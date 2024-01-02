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
@Table(name="downward_auctions")
public class DownwardAuction extends Auction {

    @Column(nullable = false)
    private Double secretMinimumPrice;

    @Column(nullable = false)
    private Double currentPrice;

    @Column(nullable = false)
    private Double decrementAmount;

    @Column(nullable = false)
    private Long decrementTime;

    @Column(nullable = false)
    private Timestamp nextDecrement;

    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private Seller owner;



}
