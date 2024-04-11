package com.ingsw.backend.controller;

import java.util.Optional;

import com.google.gson.Gson;
import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingsw.backend.enumeration.Region;
import com.ingsw.backend.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(@Qualifier("mainUserService") UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{email}/seller")
	public ResponseEntity<Seller> getSellerByEmail(@PathVariable String email) {
		Optional<Seller> user = userService.getSeller(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	@GetMapping("/{email}/buyer")
	public ResponseEntity<Buyer> getBuyerByEmail(@PathVariable String email) {
		Optional<Buyer> user = userService.getBuyer(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	
	@GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkIfEmailIsUsed(@PathVariable String email) {

		Boolean isUsed = userService.isEmailAlreadyUsed(email);
        
        return ResponseEntity.ok(isUsed);
    }

	@GetMapping("/does-account-exist/{email}/{role}")
	public ResponseEntity<Boolean> doesAccountExist(@PathVariable String email, @PathVariable Role role) {
		Boolean accountExists = userService.doesAccountExist(email, role);
		return ResponseEntity.ok(accountExists);
	}
	
	@PutMapping("/{email}/{role}/updateRegion")
    public ResponseEntity<User> updateRegion(@PathVariable String email, @PathVariable Role role, @RequestBody Region newRegion) {
		Optional<User> optionalUser = userService.updateRegion(email, role, newRegion);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}/{role}/updateBio")
    public ResponseEntity<User> updateBio(@PathVariable String email, @PathVariable Role role, @RequestBody String newBio) {
		Gson gson = new Gson();
		String bio = gson.fromJson(newBio, String.class);
		if (bio == null) {
			bio="";
		}
        Optional<User> optionalUser = userService.updateBio(email, role, bio);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


