package com.ingsw.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	
	@GetMapping("/{email}")
	public ResponseEntity<User> getByEmail(@PathVariable String email) {

		Optional<User> user = userService.getUser(email);
		
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
}

