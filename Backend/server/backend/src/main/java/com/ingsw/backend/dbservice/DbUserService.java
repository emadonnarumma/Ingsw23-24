package com.ingsw.backend.dbservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingsw.backend.model.User;
import com.ingsw.backend.repository.UserRepository;
import com.ingsw.backend.serviceinterface.UserService;

@Service("mainService")
public class DbUserService implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> getUser(String email, String password) {
		
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public User addUser(User user) {
		return userRepository.save(user);
	}
}
