package com.ingsw.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("DOWNWARD")
public class DownwardAuction extends Auction {

    private Double secretMinimumPrice;

    private Double currentPrice;
    
    private Double decrementAmount;

    private Long decrementTime;

    private Timestamp nextDecrement;



}
