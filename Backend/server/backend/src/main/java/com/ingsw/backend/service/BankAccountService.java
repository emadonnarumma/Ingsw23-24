package com.ingsw.backend.service;

import java.util.Optional;

import com.ingsw.backend.model.BankAccount;

public interface BankAccountService {
	BankAccount addBankAccount(BankAccount bankAccount);
	
	Boolean delete(Integer id);
	
	Optional<BankAccount> update(Integer id, BankAccount bankAccount);

    Optional<BankAccount> get(Integer id);
}
