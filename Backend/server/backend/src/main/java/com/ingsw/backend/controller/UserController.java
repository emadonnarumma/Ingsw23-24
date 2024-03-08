package com.ingsw.backend.controller;

import java.util.Optional;

import com.google.gson.Gson;
import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.Seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingsw.backend.enumeration.Region;
import com.ingsw.backend.model.User;
import com.ingsw.backend.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	@Qualifier("mainUserService")
	private UserService userService;

	@GetMapping("/{email}/role")
	public ResponseEntity<Role> getRoleByEmail(@PathVariable String email) {
		Optional<User> user = userService.getUser(email);

		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(user.get().getRole());
	}

	@GetMapping("/{email}/seller")
	public ResponseEntity<Seller> getSellerByEmail(@PathVariable String email) {

		Optional<Seller> user = userService.getSeller(email);
		
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(user.get());
	}

	@GetMapping("/{email}/buyer")
	public ResponseEntity<Buyer> getBuyerByEmail(@PathVariable String email) {

		Optional<Buyer> user = userService.getBuyer(email);

		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(user.get());
	}

	
	@GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkIfEmailIsUsed(@PathVariable String email) {

		Boolean isUsed = userService.isEmailAlreadyUsed(email);
        
        return ResponseEntity.ok(isUsed);
    }
	
	@PutMapping("/{email}/updateRegion")
    public ResponseEntity<User> updateRegion(@PathVariable String email, @RequestBody Region newRegion) {
        
		Optional<User> optionalUser = userService.updateRegion(email, newRegion);
        
		if (optionalUser.isPresent()) {
        
			return ResponseEntity.ok(optionalUser.get());
			
        } else {
        	
        	return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{email}/updateBio")
    public ResponseEntity<User> updateBio(@PathVariable String email, @RequestBody String newBio) {

		Gson gson = new Gson();
		String bio = gson.fromJson(newBio, String.class);
        Optional<User> optionalUser = userService.updateBio(email, bio);

        if (optionalUser.isPresent()) {
            
        	return ResponseEntity.ok(optionalUser.get());
            
        } else {
        	
        	return ResponseEntity.notFound().build();
        }
    }

	@PutMapping("/{email}/updateRole")
	public ResponseEntity<User> updateRole(@PathVariable String email, @RequestBody Role newRole) {

		Optional<User> optionalUser = userService.updateRole(email, newRole);

		if (optionalUser.isPresent()) {

			return ResponseEntity.ok(optionalUser.get());

		} else {

			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{email}/hasBankAccount")
	public ResponseEntity<Boolean> hasBankAccount(@PathVariable String email) {

		Boolean hasBankAccount = userService.hasBankAccount(email);

		return ResponseEntity.ok(hasBankAccount);
	}
}


