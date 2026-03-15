package com.neobank.backend.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class RegisterRequest {
	
	@NotEmpty
	@Size(min = 2, max = 50, message = "First Name should be between 2-50")
	private String firstName;
	
	
	@NotEmpty
	@Size(min = 2, max = 50, message = "Last Name should be between 2-50")
	private String lastName;
	
	
	@NotEmpty
	@Email(message = "Enter a valid email address")
	private String email;
	
	
	@NotEmpty
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;
	
	
	@Pattern(regexp = "^[0-9]{11}$", message = "TC Kimlik No 11 haneli olmalı")
    private String nationalId;

	
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Geçerli bir telefon numarası giriniz")
    private String phoneNumber;
	
}
