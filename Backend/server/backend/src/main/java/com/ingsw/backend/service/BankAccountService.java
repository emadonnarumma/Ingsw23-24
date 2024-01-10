package com.ingsw.backend.service;

import java.util.Optional;

import com.ingsw.backend.model.BankAccount;

public interface BankAccountService {
	
	public BankAccount addBankAccount(BankAccount bankAccount);
	
	public Boolean delete(String iban);
	
	public Optional<BankAccount> update(String iban, BankAccount bankAccount);

}
