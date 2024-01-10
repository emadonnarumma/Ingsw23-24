package com.ingsw.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingsw.backend.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String>{

}
