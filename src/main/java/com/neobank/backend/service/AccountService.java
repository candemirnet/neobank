package com.neobank.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.neobank.backend.dto.request.CreateAccountRequest;
import com.neobank.backend.dto.response.AccountResponse;
import com.neobank.backend.entity.Account;
import com.neobank.backend.entity.User;
import com.neobank.backend.entity.enums.AccountStatus;
import com.neobank.backend.exception.BusinessException;
import com.neobank.backend.repository.AccountRepository;
import com.neobank.backend.repository.UserRepository;
import com.neobank.backend.util.IbanGenerator;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IbanGenerator ibanGenerator;
	
	@Autowired
	private AccountRepository accountRepository;
	
	private User getCurrentUser() { // get logged-in user
		
		String email = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
		
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
		
	}
	
	// turn entity to response
	private AccountResponse toResponse(Account account) {
		
		return AccountResponse.builder()
				.id(account.getId())
				.accountName(account.getAccountName())
				.iban(account.getIban())
				.balance(account.getBalance())
				.currency(account.getCurrency())
				.accountType(account.getAccountType())
				.status(account.getStatus())
				.dailyLimit(account.getDailyLimit())
				.createdAt(account.getCreatedAt())
				.build();
		
	}
	
	@Transactional
	public AccountResponse createAccount(CreateAccountRequest request) {
		
		User user = getCurrentUser();
		
		
		Account account = Account.builder()
				.user(user)
				.accountName(request.getAccountName())
				.iban(ibanGenerator.generate())
				.currency(request.getCurrency())
				.accountType(request.getAccountType())
				.build();
		
		accountRepository.save(account);
		
		return toResponse(account);
		
	}
	
	
	public List<AccountResponse> getMyAccounts(){
		
		
		User user = getCurrentUser();
		
		return accountRepository.findByUser(user)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
		
	}
	
	public AccountResponse getAccountById(Long id) {
		
		
		User user = getCurrentUser();
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new BusinessException("Account not found!", HttpStatus.NOT_FOUND));
		
		if (!account.getUser().getId().equals(user.getId())) {
			throw new BusinessException("You don't have any permission to acces this account", HttpStatus.FORBIDDEN);
		}
		
		return toResponse(account);
		
	}
	
	public AccountResponse getAccountByIban(String iban) {
		
		Account account = accountRepository.findByIban(iban)
				.orElseThrow(() -> new BusinessException("No account was found for this account", HttpStatus.NOT_FOUND));
		return toResponse(account);
	}
	
	@Transactional
    public AccountResponse updateAccountName(Long id, String newName) {
		
        User user = getCurrentUser();
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new BusinessException(
                "Hesap bulunamadı", HttpStatus.NOT_FOUND
            ));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new BusinessException(
                "Bu hesaba erişim yetkiniz yok", HttpStatus.FORBIDDEN
            );
        }

        account.setAccountName(newName);
        accountRepository.save(account);
        return toResponse(account);
    }
	
	@Transactional
    public AccountResponse updateAccountStatus(Long id, AccountStatus newStatus) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new BusinessException(
                "Hesap bulunamadı", HttpStatus.NOT_FOUND
            ));

        account.setStatus(newStatus);
        accountRepository.save(account);
        return toResponse(account);
    }
	
}
