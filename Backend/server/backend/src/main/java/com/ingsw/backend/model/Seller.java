package com.ingsw.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;



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
    @JsonManagedReference("seller-bankAccount")
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SilentAuction> silentAuctions;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DownwardAuction> downwardAuctions;

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    @JsonManagedReference("seller-reverseBid")
    private List<ReverseBid> bids;


}
