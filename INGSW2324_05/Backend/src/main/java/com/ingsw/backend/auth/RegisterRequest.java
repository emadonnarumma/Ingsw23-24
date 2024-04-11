package com.ingsw.backend.auth;

import com.ingsw.backend.enumeration.Region;
import com.ingsw.backend.enumeration.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private Role role;
	private String email;
	private String bio;
	private String password;
	private Region region;
	private String name;

}
