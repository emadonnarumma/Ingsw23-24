package com.ingsw.backend.service;

import java.util.Optional;

import com.ingsw.backend.model.User;

public interface UserService {

	public User addUser(User user);
	public Optional<User> getUser(String email, String password);
	public Optional<User> getUser(String email);
}
