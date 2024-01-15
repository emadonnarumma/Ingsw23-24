package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "type"
		)
@JsonSubTypes({
		  @JsonSubTypes.Type(value = SilentBid.class, name = "SILENT"),
		  @JsonSubTypes.Type(value = ReverseBid.class, name = "REVERSE")
				})
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
