package com.ingsw.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingsw.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	public Optional<User> findByEmailAndPassword(String email, String password);
	
	public Optional<User> findByEmail(String email);
}
