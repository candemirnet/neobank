package com.neobank.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
	@NotBlank(message = "Email mustn't be blank!")
	@Email(message = "Enter a valid email address")
	private String email;
	
	@NotBlank(message = "Password mustn't be blank!")
	private String password;

}
