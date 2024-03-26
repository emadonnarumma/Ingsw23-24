package com.ingsw.backend.repository;

import java.util.Optional;

import com.ingsw.backend.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String>{

	public Optional<User> findByEmailAndRoleAndPassword(String email, Role role, String password);
	
	public Optional<User> findByEmailAndRole(String email, Role role);

	public Boolean existsByEmail(String email);

	public Boolean existsByEmailAndRole(String email, Role role);
 }
