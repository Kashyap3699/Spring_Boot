package com.banking.service.Impli;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.Account;
import com.banking.repository.BankRepository;
import com.banking.service.AccountService;

@Service
public class AccountServiceImplimentation implements AccountService {
	@Autowired
	BankRepository repo;

	@Override
	public Account createAccount(Account account) {
		// TODO Auto-generated method stub
		Account save = repo.save(account);
		return save;
	}

	@Override
	public Account getAccountDetailByAccountNumber(Long accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account deposit(Long accountNumber, Double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account withdraw(Long accountNumber, Double amount) {
		// TODO Auto-generated method stub
		return null;
	}

}
