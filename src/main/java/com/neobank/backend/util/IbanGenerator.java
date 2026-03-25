package com.neobank.backend.util;

import java.util.Random;


import org.springframework.stereotype.Component;

import com.neobank.backend.repository.AccountRepository;

import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public class IbanGenerator {

	
	private final AccountRepository accountRepository;
	
	private static final String COUNTRY_CODE = "TR";
	private static final Random RANDOM = new Random();
	
	public String generate() {
		String iban;
		
		do {
			iban = COUNTRY_CODE + generateNumericPart();
		} while (accountRepository.existsByIban(iban));
		
		return iban;
	}
	
	private String generateNumericPart() {
		
		StringBuilder sb = new StringBuilder();
		
		for(int i =0; i < 24; i++) {
			
			sb.append(RANDOM.nextInt(10));
			
		}
		
		return sb.toString();
		
	}
	
	
	
}
