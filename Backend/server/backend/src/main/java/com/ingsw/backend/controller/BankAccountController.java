package com.ingsw.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingsw.backend.model.BankAccount;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.service.BankAccountService;
import com.ingsw.backend.service.UserService;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final UserService userService;

    @Autowired
    public BankAccountController(@Qualifier("mainBankAccountService") BankAccountService bankAccountService,
                                 @Qualifier("mainUserService") UserService userService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;
    }

    @PostMapping("/{email}")
    public ResponseEntity<BankAccount> addBankAccount(@Valid @RequestBody BankAccount bankAccount, @PathVariable String email) {
        
    	Optional<Seller> seller = userService.getSeller(email);
        
		if (seller.isEmpty()) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
        bankAccount.setSeller(seller.get());

        BankAccount savedBankAccount= bankAccountService.addBankAccount(bankAccount);

        return new ResponseEntity<>(savedBankAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable Integer id, @Valid @RequestBody BankAccount bankAccount) {

        Optional<BankAccount> existingBankAccount = bankAccountService.get(id);
        if (existingBankAccount.isPresent())
            bankAccount.setSeller(existingBankAccount.get().getSeller());
        else {

            return ResponseEntity.notFound().build();
        }

    	Optional<BankAccount> updatedBankAccount = bankAccountService.update(id, bankAccount);
        
    	if (updatedBankAccount.isPresent()) {
        	
            return ResponseEntity.ok(updatedBankAccount.get());
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Integer id) {
        
    	if (Boolean.TRUE.equals(bankAccountService.delete(id))) {
    		
            return ResponseEntity.noContent().build();
            
        } else {
        	
            return ResponseEntity.notFound().build();
        }
    }
}
