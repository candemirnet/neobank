package com.neobank.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.backend.dto.request.LoginRequest;
import com.neobank.backend.dto.request.RefreshTokenRequest;
import com.neobank.backend.dto.request.RegisterRequest;
import com.neobank.backend.dto.response.AuthResponse;
import com.neobank.backend.service.AuthService;


import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest)  {
		

		return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(authService.register(registerRequest));

	    
	}
	 
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
		
		return ResponseEntity.ok(authService.login(request));
		
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request){
		
		return ResponseEntity.ok(authService.refresh(request));
		
	}

}
