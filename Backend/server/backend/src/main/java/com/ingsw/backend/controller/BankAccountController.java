package com.ingsw.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingsw.backend.model.BankAccount;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.User;
import com.ingsw.backend.service.BankAccountService;
import com.ingsw.backend.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/bankaccount")
public class BankAccountController {

    @Autowired
    @Qualifier("mainBankAccountService")
    private BankAccountService bankAccountService;
    
	@Autowired
	@Qualifier("mainUserService")
	private UserService userService;

    @PostMapping
    public ResponseEntity<BankAccount> addBankAccount(@RequestBody BankAccount bankAccount) {
        
    	Optional<User> seller = userService.getUser(bankAccount.getSeller().getEmail());
        
		if (seller.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
        bankAccount.setSeller((Seller) seller.get());

        BankAccount savedBankAccount= bankAccountService.addBankAccount(bankAccount);

        return new ResponseEntity<>(savedBankAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{iban}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable String iban, @RequestBody BankAccount bankAccount) {
       
    	Optional<BankAccount> updatedBankAccount = bankAccountService.update(iban, bankAccount);
        
    	if (updatedBankAccount.isPresent()) {
        	
            return ResponseEntity.ok(updatedBankAccount.get());
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{iban}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable String iban) {
        
    	if (bankAccountService.delete(iban)) {
    		
            return ResponseEntity.noContent().build();
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }
}
