package com.neobank.backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neobank.backend.entity.Account;
import com.neobank.backend.entity.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByUser(User user);
	
	Optional<Account> findByIban(String iban);
	
	boolean existsByIban(String iban);
}
