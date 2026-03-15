package com.neobank.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neobank.backend.dto.request.LoginRequest;
import com.neobank.backend.dto.request.RegisterRequest;
import com.neobank.backend.dto.response.AuthResponse;
import com.neobank.backend.entity.Role;
import com.neobank.backend.entity.User;
import com.neobank.backend.entity.enums.RoleName;
import com.neobank.backend.exception.BusinessException;
import com.neobank.backend.repository.RoleRepository;
import com.neobank.backend.repository.UserRepository;
import com.neobank.backend.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Transactional
	public AuthResponse register(RegisterRequest request) {
		
		// email check
		if(userRepository.existsByEmail(request.getEmail())) {
			
			throw new BusinessException("This email address is already in use.", HttpStatus.CONFLICT);
		}
		
		// national id check
		if(request.getNationalId() != null && userRepository.existsByNationalId(request.getNationalId())) {
			
			throw new BusinessException("This national id is already registered", HttpStatus.CONFLICT);
			
		}
		
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new BusinessException(
						"Role not found", HttpStatus.INTERNAL_SERVER_ERROR
				));
		
		//Create User
		User user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.passwordHash(passwordEncoder.encode(request.getPassword()))
				.nationalId(request.getNationalId())
				.phoneNumber(request.getPhoneNumber())
				.isActive(true)
				.build();
		
		
		user.getRoles().add(userRole);
		userRepository.save(user);
		
		//Create token
		String accessToken = jwtUtil.generateAccessToken(user);
		String refreshToken = jwtUtil.generateRefreshToken(user);
		
		return AuthResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.tokenType("Bearer")
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.build();
		
	}
	
	public AuthResponse login(LoginRequest request) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
				
				);
		
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new BusinessException("User not found!", HttpStatus.NOT_FOUND));
		
		String accessToken = jwtUtil.generateAccessToken(user);
		String refreshToken = jwtUtil.generateRefreshToken(user);
		
		return AuthResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.tokenType("Bearer")
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.build();
		
		
	}

}
