package com.ingsw.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.ingsw.backend.model.User;
import com.ingsw.backend.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	@Qualifier("mainService")
	private UserService userService;
	
//	@GetMapping
//	public ResponseEntity<String> sayHello() {
//		return ResponseEntity.ok("Ciao Mondo");
//		
//		
//	}
//	
	@GetMapping("/{email}")
	public ResponseEntity<User> getByEmailAndPassword(@PathVariable String email) {

		Optional<User> user = userService.getUser(email);
		
		if (user.isEmpty()) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		
		return ResponseEntity.ok(user.get());
	}

//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//	public User addUser(@RequestBody Buyer user) {
//		return userService.addUser(user);
//	}
}
