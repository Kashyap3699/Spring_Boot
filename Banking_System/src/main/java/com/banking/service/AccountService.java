package com.banking.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.banking.entity.Account;

public interface AccountService {

	public ResponseEntity<Account> createAccount(Account account);

	public ResponseEntity<Account> getAccountDetailByAccountNumber(Long accountNumber);

	public ResponseEntity<List<Account>> getAllAccounts();

	public ResponseEntity<Account> deposit(Long accountNumber, Double amount);

	public ResponseEntity<Account> withdraw(Long accountNumber, Double amount);
}
