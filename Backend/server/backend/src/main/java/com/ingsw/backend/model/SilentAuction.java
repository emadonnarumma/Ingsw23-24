package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("SILENT")
public class SilentAuction extends Auction {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", locale = "it_IT", timezone = "Europe/Rome")
    private Timestamp expirationDate;

    private Long withdrawalTime;

    @OneToMany(mappedBy = "silentAuction", fetch = FetchType.EAGER)
    @JsonManagedReference("silentAuction-silentBid")
    private List<SilentBid> receivedBids;
}
