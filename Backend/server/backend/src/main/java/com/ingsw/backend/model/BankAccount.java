package com.ingsw.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bank_accounts")
public class BankAccount {

    @OneToOne
    @JoinColumn(name = "seller_email", referencedColumnName = "email")
    private Seller seller;

    @Id
    @Length(min=27, max=27)
    @Column(nullable = false, length = 27)
    private String iban;

    @Length(min=11, max=11)
    @Column(nullable = false, length = 11)
    private String iva;


}
