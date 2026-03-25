package com.neobank.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neobank.backend.entity.enums.AccountType;
import com.neobank.backend.entity.enums.CurrencyType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateAccountRequest {
	
	
	private String accountName;
	
	@JsonProperty("accountType")
	@NotNull(message = "Account Type cannot be empty")
	private AccountType accountType;
	
	@JsonProperty("currency")
	@NotNull(message = "Currency Type cannot be empty")
	private CurrencyType currency;
	

}
