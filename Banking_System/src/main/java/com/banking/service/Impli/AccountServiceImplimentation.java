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

@Service
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
		Optional<Account> byId = null;
		try {
			byId = repo.findById(accountNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return byId.get();

	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account deposit(Long accountNumber, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account withdraw(Long accountNumber, double amount) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Scheduled(fixedRate = 120000)
	@Transactional
	public void deductAmountFromAccounts() {
        // Fetch all accounts with balance greater than 0
        List<Account> accounts = repo.findByBalanceGreaterThan(0);
        
        for (Account account : accounts) {
            double currentBalance = account.getAccountBalance();
            if (currentBalance < 200) {
                // Set balance to 0 if it's less than 200
                account.setAccountBalance(0);;
            } else {
                // Deduct 200 rupees from the balance
                account.setAccountBalance(currentBalance-200);
            }
            repo.save(account);
        }
    }
	
	

}
