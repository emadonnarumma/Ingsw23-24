package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
		  @JsonSubTypes.Type(value = ReverseBid.class, name = "REVERSE"),
          @JsonSubTypes.Type(value = DownwardBid.class, name = "DOWNWARD")
				})
public abstract class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBid;

    @Column(nullable = false)
    private Double moneyAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BidStatus status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", locale = "it_IT", timezone = "Europe/Rome")
    @Column(nullable = false)
    private Timestamp timestamp;
}
