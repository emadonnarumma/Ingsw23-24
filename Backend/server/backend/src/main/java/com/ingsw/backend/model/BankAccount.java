package com.ingsw.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bank_accounts")
public class BankAccount {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBankAccount;

    @OneToOne
    @JoinColumn(name = "seller_email", referencedColumnName = "email")
    @JsonIgnore
    private Seller seller;

    @Length(min=27, max=27)
    @Column(nullable = false, length = 27)
    private String iban;

    @Length(min=11, max=11)
    @Column(nullable = false, length = 11)
    private String iva;


}
