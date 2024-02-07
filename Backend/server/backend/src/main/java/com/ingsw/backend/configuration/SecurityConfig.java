package com.ingsw.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;


	
	   @Bean
	   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	                .csrf(csrf -> csrf
	                        .disable()
	                )
	                .sessionManagement(session -> session
	                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                )
	                .authorizeHttpRequests(authorize -> authorize
	                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/user/check-email/{email}").permitAll()
	                        .anyRequest().authenticated()
	                )
	                .authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
}
