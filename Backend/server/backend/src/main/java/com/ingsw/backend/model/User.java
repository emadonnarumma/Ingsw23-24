package com.ingsw.backend.model;

import com.ingsw.backend.enumeration.Region;

import jakarta.persistence.*;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
	
	private String username;
	
	@Id
	private String email;
	
	private String password;
	
	private String bio;
	
	@Enumerated(EnumType.STRING)
	private Region region;

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getBio() {
		return bio;
	}

	public Region getRegion() {
		return region;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
	

}
