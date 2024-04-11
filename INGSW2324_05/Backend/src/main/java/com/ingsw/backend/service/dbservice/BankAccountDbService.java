package com.ingsw.backend.service.dbservice;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ingsw.backend.model.BankAccount;
import com.ingsw.backend.repository.BankAccountRepository;
import com.ingsw.backend.service.BankAccountService;

@Service("mainBankAccountService")
public class BankAccountDbService implements BankAccountService {
	
	private final BankAccountRepository bankAccountRepository;
	

	public BankAccountDbService(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}

	@Override
	public BankAccount addBankAccount(BankAccount bankAccount) {
		
		return bankAccountRepository.save(bankAccount);
	}

	@Override
	public Boolean delete(Integer id) {
	    
		if (!bankAccountRepository.existsById(id)) {
	        return false;
	    }
	    
	    bankAccountRepository.deleteById(id);
	    
	    return true;	
	}

	@Override
	public Optional<BankAccount> update(Integer id, BankAccount bankAccount) {
	    
		if (!bankAccountRepository.existsById(id)) {
	        return Optional.empty();
	    }

		bankAccount.setIdBankAccount(id);
	    BankAccount updatedBankAccount = bankAccountRepository.save(bankAccount);
	    
	    return Optional.of(updatedBankAccount);
	}

	@Override
	public Optional<BankAccount> get(Integer id) {
		if(!bankAccountRepository.existsById(id)) {
			return Optional.empty();
		}

		return bankAccountRepository.findById(id);
	}


}
