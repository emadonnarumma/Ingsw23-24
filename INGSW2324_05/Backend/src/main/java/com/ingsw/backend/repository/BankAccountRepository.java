package com.ingsw.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer>{
}
