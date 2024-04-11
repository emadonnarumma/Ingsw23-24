package com.ingsw.backend.auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ingsw.backend.configuration.JwtService;
import com.ingsw.backend.enumeration.Role;
import com.ingsw.backend.model.Buyer;
import com.ingsw.backend.model.Seller;
import com.ingsw.backend.model.User;
import com.ingsw.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {

		User user;

		if (request.getRole() == Role.BUYER) {

			user = Buyer.builder()
					.bio(request.getBio())
					.email(request.getEmail())
					.password(passwordEncoder.encode(request.getPassword()))
					.name(request.getName())
					.region(request.getRegion())
					.role(request.getRole())
					.build();

		} else {

			user = Seller.builder()
					.bio(request.getBio())
					.email(request.getEmail())
					.password(passwordEncoder.encode(request.getPassword()))
					.name(request.getName())
					.region(request.getRegion())
					.role(request.getRole())
					.build();
		}

		repository.save(user);

		var jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();


	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		var user = repository.findByEmailAndRole(request.getEmail(), request.getRole())
					.orElseThrow();

		var jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}


}
