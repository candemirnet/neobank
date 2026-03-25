package com.neobank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.neobank.backend.entity.Account;
import com.neobank.backend.entity.Transaction;
import java.util.UUID;
import com.neobank.backend.entity.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	// All transactions associated with the account (including pagination)
	Page<Transaction> findBySenderAccountOrReceiverAccount(
			Account senderAccount,
			Account receiverAccount,
			Pageable pageable);
	
	Optional<Transaction> findByReferenceNo(UUID referenceNo);
	
	// Filter by account and type
	Page<Transaction> findBySenderAccountOrReceiverAccountAndType(
			Account senderAccount,
			Account receiverAccount,
			TransactionType type,
			Pageable pageable);
	
}
