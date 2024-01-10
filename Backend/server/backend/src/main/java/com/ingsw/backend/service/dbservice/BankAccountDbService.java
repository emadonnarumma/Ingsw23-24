package com.ingsw.backend.service.dbservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingsw.backend.model.BankAccount;
import com.ingsw.backend.repository.BankAccountRepository;
import com.ingsw.backend.service.BankAccountService;

@Service("mainBankAccountService")
public class BankAccountDbService implements BankAccountService {
	
	private final BankAccountRepository bankAccountRepository;
	
	@Autowired
	public BankAccountDbService(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}

	@Override
	public BankAccount addBankAccount(BankAccount bankAccount) {
		
		return bankAccountRepository.save(bankAccount);
	}

	@Override
	public Boolean delete(String iban) {
	    
		if (!bankAccountRepository.existsById(iban)) {
	        return false;
	    }
	    
	    bankAccountRepository.deleteById(iban);
	    
	    return true;	
	}

	@Override
	public Optional<BankAccount> update(String iban, BankAccount bankAccount) {
	    
		if (!bankAccountRepository.existsById(iban)) {
	        return Optional.empty();
	    }

	    BankAccount updatedBankAccount = bankAccountRepository.save(bankAccount);
	    
	    return Optional.of(updatedBankAccount);
	}


}
