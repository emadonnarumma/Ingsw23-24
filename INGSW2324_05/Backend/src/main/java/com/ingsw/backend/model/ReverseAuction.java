package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("REVERSE")
public class ReverseAuction extends Auction {

    private Double startingPrice;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", locale = "it_IT", timezone = "Europe/Rome")
    private Timestamp expirationDate;
    
    @OneToMany(mappedBy = "reverseAuction", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<ReverseBid> receivedBids;
}
