package com.ingsw.backend.repository;

import java.util.Optional;

import com.ingsw.backend.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ingsw.backend.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByEmailAndRole(String email, Role role);

	Boolean existsByEmail(String email);
	Boolean existsByEmailAndRole(String email, Role role);
 }
