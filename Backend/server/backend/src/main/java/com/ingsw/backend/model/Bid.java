package com.ingsw.backend.model;

import com.ingsw.backend.enumeration.BidStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bids")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double moneyAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BidStatus status;

    @Column(nullable = false)
    private Timestamp timestamp;


}
