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
@Table(name = "silent_auctions")
public class SilentAuction extends Auction {

    @Column(nullable = false)
    private Timestamp expirationDate;

    @Column(nullable = false)
    private Long withdrawalTime;


    @ManyToOne
    @JoinColumn(name = "owner_email", referencedColumnName = "email")
    private Seller owner;


}
