package com.ingsw.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String>{

	public Optional<User> findByEmailAndPassword(String email, String password);
	
	public Optional<User> findByEmail(String email);

	public Boolean existsByEmail(String email);

	@Query("SELECT COUNT(b) > 0 FROM BankAccount b WHERE b.seller.email = :email")
	public Boolean hasBankAccount(String email);
 }
