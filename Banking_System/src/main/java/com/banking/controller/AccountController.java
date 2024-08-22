package com.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.Account;
import com.banking.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

	@Autowired
	AccountService accountservice;

	@PostMapping("/createAccount")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		try {
			Account save = accountservice.createAccount(account);
			log.info("Account created successfully");
			return ResponseEntity.status(HttpStatus.CREATED).body(save);

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error creating account: {}", e.getMessage(), e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable Long accountNumber) {
		Account accountDetailByAccountNumber = accountservice.getAccountDetailByAccountNumber(accountNumber);
		if (accountDetailByAccountNumber == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(accountDetailByAccountNumber);
	}

	@GetMapping("/allAccount")
	public ResponseEntity<List<Account>> getAllAccount() {
		List<Account> allAccounts = accountservice.getAllAccounts();
		if (allAccounts.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(allAccounts);
	}

	@PutMapping("/deposit/{accountNumber}/{amount}")
	public ResponseEntity<Account> depositAmount(@PathVariable("accountNumber") Long accountNumber,
			@PathVariable("amount") double amount) {
		log.info("Received request to deposit {} into account {}", amount, accountNumber);
		try {
			if (amount <= 0) {
				log.warn("Deposit amount must be greater than zero. Received amount: {}", amount);
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			Account updatedAccount = accountservice.deposit(accountNumber, amount);
			double accountBalanceAfterdeposit = updatedAccount.getAccountBalance();
			log.info("Successfully deposit {} from account {}. New balance: {}", amount, accountNumber,
					accountBalanceAfterdeposit);
			return new ResponseEntity<>(updatedAccount, HttpStatus.OK);

		} catch (RuntimeException e) {
			log.error("Error occurred while depositing {} into account {}: {}", amount, accountNumber, e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		}
	}

	@PutMapping("/withdraw/{accountNumber}/{amount}")
	public ResponseEntity<Account> withdrawAmount(@PathVariable("accountNumber") Long accountNumber,
			@PathVariable("amount") double amount) {
		log.info("Received request to withdraw {} into account {}", amount, accountNumber);
		try {
			if (amount <= 0) {
				log.warn("Withdraw amount must be greater than zero. Received amount: {}", amount);
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			Account updatedAccount = accountservice.withdraw(accountNumber, amount);
			double accountBalanceAfterWithdraw = updatedAccount.getAccountBalance();
			log.info("Successfully withdrew {} from account {}. New balance: {}", amount, accountNumber,
					accountBalanceAfterWithdraw);
			return new ResponseEntity<>(updatedAccount, HttpStatus.OK);

		} catch (RuntimeException e) {
			log.error("Error occurred while withdraw {} into account {}: {}", amount, accountNumber, e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		}
	}
}
