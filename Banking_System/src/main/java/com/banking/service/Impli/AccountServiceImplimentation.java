package com.banking.service.Impli;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	AccountRepository repo;

	@Override
	public Account createAccount(Account account) {
		// TODO Auto-generated method stub
		Account save = repo.save(account);
		return save;
	}

	@Override
	public Account getAccountDetailByAccountNumber(Long accountNumber) {
		// TODO Auto-generated method stub
		Optional<Account> byId = repo.findById(accountNumber);
	    
	    // Return the account if found, otherwise return null
	    return byId.orElse(null);

	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		List<Account> allaccounts = repo.findAll();
		return allaccounts;
	}

	@Override
	public Account deposit(Long accountNumber, double amount) {
		// TODO Auto-generated method stub
		Optional<Account> byId = repo.findById(accountNumber);
		if (byId.isEmpty()) {
			throw new RuntimeException("Account is not available");
		}
		Account account = byId.get();

		account.setAccountBalance(account.getAccountBalance() + amount);
		return repo.save(account);
	}

	@Override
	public Account withdraw(Long accountNumber, double amount) {
		// TODO Auto-generated method stub
		Optional<Account> byId = repo.findById(accountNumber);
		if (byId.isEmpty()) {
			throw new RuntimeException("Account is not available");
		}
		Account account = byId.get();
		if(account.getAccountBalance()<amount) {
			throw new RuntimeException("Insufficient funds");
		}
		account.setAccountBalance(account.getAccountBalance() - amount);
		return repo.save(account);
	}

	@Scheduled(fixedRate = 120000)
	@Transactional
	public void deductAmountFromAccounts() {
		// Fetch all accounts with balance greater than 0
		log.info("Scheduled task started");
		List<Account> accounts = repo.findByAccountBalanceGreaterThan(0);

		for (Account account : accounts) {
			double currentBalance = account.getAccountBalance();
			if (currentBalance < 200) {
				account.setAccountBalance(0);
				
			} else {
				 log.info("Deducting 200 from account {}", account.getAccountNumber());
				account.setAccountBalance(currentBalance - 200);
			}
			repo.save(account);
			log.info("Account {} updated successfully", account.getAccountNumber());
		}
		log.info("Scheduled task completed");
	}

}
