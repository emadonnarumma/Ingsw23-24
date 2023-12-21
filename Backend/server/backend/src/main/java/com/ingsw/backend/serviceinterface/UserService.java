package com.ingsw.backend.serviceinterface;

import java.util.Optional;

import com.ingsw.backend.model.User;

public interface UserService {

	public Optional<User> getUser(String email, String password);
}
