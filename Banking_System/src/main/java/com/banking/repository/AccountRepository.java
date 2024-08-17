package com.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByBalanceGreaterThan(double amount);

}
