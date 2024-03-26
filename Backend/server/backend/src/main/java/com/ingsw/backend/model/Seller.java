package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;




@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("SELLER")
public class Seller extends User {

    @OneToOne(mappedBy = "seller")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<SilentAuction> silentAuctions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<DownwardAuction> downwardAuctions;

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ReverseBid> bids;
}
