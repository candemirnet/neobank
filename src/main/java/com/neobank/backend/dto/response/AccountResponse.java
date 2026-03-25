package com.neobank.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.neobank.backend.entity.enums.AccountStatus;
import com.neobank.backend.entity.enums.AccountType;
import com.neobank.backend.entity.enums.CurrencyType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountResponse {

	private Long id;
	private String accountName;
	private String iban;
	private BigDecimal balance;
	private CurrencyType currency;
	private AccountType accountType;
	private AccountStatus status;
	private BigDecimal dailyLimit;
	private LocalDateTime createdAt;
}
