package com.neobank.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.neobank.backend.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

   
	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								// Açık endpoint'ler
								"/api/v1/auth/**",
			                    "/swagger-ui/**",
			                    "/swagger-ui.html",
			                    "/v3/api-docs/**").permitAll()
								// Admin endpoint'leri
								.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
								.anyRequest()
								.authenticated()
								)
						.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						.authenticationProvider(authenticationProvider)
						.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
						.build();
		
	}
	
}
