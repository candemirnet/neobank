package com.neobank.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.neobank.backend.dto.request.CreateAccountRequest;
import com.neobank.backend.dto.response.AccountResponse;
import com.neobank.backend.entity.enums.AccountStatus;
import com.neobank.backend.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public ResponseEntity<AccountResponse> createAccount(
			@Valid @RequestBody CreateAccountRequest request
			){
		
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(accountService.createAccount(request));
		
	}
	
	@GetMapping
	public ResponseEntity<List<AccountResponse>> getMyAccounts(){
		
		return ResponseEntity.ok(accountService.getMyAccounts());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id){
		
		return ResponseEntity.ok(accountService.getAccountById(id));
		
	}
	
	@GetMapping("/iban/{iban}")
	public ResponseEntity<AccountResponse> getAccountByIban(@PathVariable String iban){
		
		return ResponseEntity.ok(accountService.getAccountByIban(iban));
		
	}
	
	
	@PutMapping("/{id}/name")
	public ResponseEntity<AccountResponse> updateAccountName(
			@PathVariable Long id,
			@RequestBody Map<String, String> body
			
			){
		
		return ResponseEntity.ok(accountService.updateAccountName(id, body.get("accountName")));
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<AccountResponse> updateAccountStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        AccountStatus status = AccountStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(
            accountService.updateAccountStatus(id, status)
        );
    }
	
	
}
