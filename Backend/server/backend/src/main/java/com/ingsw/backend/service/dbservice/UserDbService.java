package com.ingsw.backend.service.dbservice;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ingsw.backend.model.User;
import com.ingsw.backend.repository.UserRepository;
import com.ingsw.backend.service.UserService;

@Service("mainUserService")
public class UserDbService implements UserService{

	private final UserRepository userRepository;


	public UserDbService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public User addUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> getUser(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Boolean isEmailAlreadyUsed(String email) {
		
		return userRepository.existsByEmail(email);
	}
}
