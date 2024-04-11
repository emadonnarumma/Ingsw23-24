package com.ingsw.backend.auth;

import com.ingsw.backend.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
	
	private String email;
	private Role role;
	private String password;

}
