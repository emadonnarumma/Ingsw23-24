package com.ingsw.backend.service;

import java.util.Optional;

import com.ingsw.backend.enumeration.Region;
import com.ingsw.backend.model.User;

public interface UserService {

	public User addUser(User user);
	
	public Optional<User> getUser(String email, String password);
	
	public Optional<User> getUser(String email);
	
	public Boolean isEmailAlreadyUsed(String email);

	public Optional<User> updateRegion(String email, Region newRegion);

	public Optional<User> updateBio(String email, String newBio);
}
