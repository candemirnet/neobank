package com.neobank.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";
	private String email;
	private String firstName;
	private String lastName;

}
