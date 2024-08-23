package com.banking.service.Impli;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.banking.entity.Account;
import com.banking.repository.AccountRepository;
import com.banking.service.AccountService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImplimentation implements AccountService {
	@Autowired
	AccountRepository accountRepository;


	@Override
	public ResponseEntity<Account> createAccount(Account account) {
		// Validate input data

		try {
			if (account == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			if (account.getAccountType() == null || account.getAccountType().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			// Validate Account balance
			if (account.getAccountBalance() < 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Negative balance is not allowed
			}
			// Save the account
			Account newAccount = new Account().builder().accountBalance(account.getAccountBalance())
					.accountType(account.getAccountType())
					.users(account.getUsers())
					.build();
			Account savedAccount = accountRepository.save(newAccount);
			return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
		} catch (Exception e) {
			// Log and handle exception
			log.error("Error occurred while creating account: {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<Account> getAccountDetailByAccountNumber(Long accountNumber) {
		try {
			Optional<Account> optionalAccount = accountRepository.findById(accountNumber);
			if (optionalAccount.isPresent()) {
				Account account = optionalAccount.get();
				return new ResponseEntity<>(account, HttpStatus.FOUND);

			} else {
				log.info("Account not found with accountNumber :: {}", accountNumber);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error occurred while getting account {}: {}", accountNumber, e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	@Override
	public ResponseEntity<List<Account>> getAllAccounts() {
		try {
			List<Account> allAccounts = accountRepository.findAll();

			if (allAccounts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			} else {
				return new ResponseEntity<>(allAccounts, HttpStatus.OK);
			}

		} catch (Exception e) {
			// Log the exception
			log.error("Error occurred while fetching all accounts: {}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<Account> deposit(Long accountNumber, Double amount) {
		try {

			if (amount <= 0) {
				log.error("Deposit amount must be greater than zero. Received amount: {}", amount);
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			Optional<Account> optionalAccount = accountRepository.findById(accountNumber);

			if (optionalAccount.isPresent()) {

				Account account = optionalAccount.get();
				account.setAccountBalance(account.getAccountBalance() + amount);
				Account updatedAccount = accountRepository.save(account);

				return new ResponseEntity<>(updatedAccount, HttpStatus.OK);

			} else {
				log.info("Account not found with accountNumber :: {}", accountNumber);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error occurred while deposit {} into account {}: {}", amount, accountNumber, e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Account> withdraw(Long accountNumber, Double amount) {

		try {

			if (amount <= 0) {
				log.error("Withdraw amount must be greater than zero. Received amount: {}", amount);
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			Optional<Account> optionalAccount = accountRepository.findById(accountNumber);

			if (optionalAccount.isPresent()) {

				Account account = optionalAccount.get();

				if (account.getAccountBalance() == null) {
					log.error("Account balance is null for accountNumber :: {}", accountNumber);
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				if (account.getAccountBalance() < amount) {
					log.error("Insufficient funds for accountNumber :: {}. Requested amount: {}, Available balance: {}",
							accountNumber, amount, account.getAccountBalance());
					return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
				}
				account.setAccountBalance(account.getAccountBalance() - amount);
				Account updatedAccount = accountRepository.save(account);

				return new ResponseEntity<>(updatedAccount, HttpStatus.OK);

			} else {
				log.info("Account not found with accountNumber :: {}", accountNumber);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error occurred while withdraw {} into account {}: {}", amount, accountNumber, e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Scheduled(fixedRate = 120000)
	@Transactional
	public void deductAmountFromAccounts() {
		// Fetch all accounts with balance greater than 0
		log.info("Scheduled task started");
		List<Account> accounts = accountRepository.findByAccountBalanceGreaterThan(0);

		for (Account account : accounts) {
			double currentBalance = account.getAccountBalance();
			if (currentBalance < 200) {
				account.setAccountBalance((double) 0);

			} else {
				log.info("Deducting 200 from account {}", account.getAccountNumber());
				account.setAccountBalance(currentBalance - 200);
			}
			accountRepository.save(account);
			log.info("Account {} updated successfully", account.getAccountNumber());
		}
		log.info("Scheduled task completed");
	}

}
