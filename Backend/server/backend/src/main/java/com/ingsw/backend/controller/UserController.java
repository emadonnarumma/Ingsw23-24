package com.ingsw.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ingsw.backend.model.User;
import com.ingsw.backend.serviceinterface.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	@Qualifier("mainService")
	private UserService userService;

	
	@GetMapping("/{email}/{password}")
	public User getByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
		
		Optional<User> user = userService.getUser(email, password);
		
		if (user.isEmpty()) {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		
		return user.get();
	}
}