package com.ingsw.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
    @JoinColumns({
            @JoinColumn(name="seller_email", referencedColumnName="email"),
            @JoinColumn(name="seller_role", referencedColumnName="role")
    })
    @JsonIgnore
    private Seller seller;

    @Length(min=27, max=27)
    @Column(nullable = false, length = 27)
    private String iban;

    @Length(min=11, max=11)
    @Column(nullable = false, length = 11)
    private String iva;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((iban == null) ? 0 : iban.hashCode());
        result = prime * result + ((iva == null) ? 0 : iva.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BankAccount other = (BankAccount) obj;
        if (iban == null) {
            if (other.iban != null)
                return false;
        } else if (!iban.equals(other.iban))
            return false;
        if (iva == null) {
            return other.iva == null;
        } else return iva.equals(other.iva);
    }
}
