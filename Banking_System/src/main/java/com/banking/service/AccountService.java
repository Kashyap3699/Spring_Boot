package com.banking.service;

import java.util.List;

import com.banking.entity.Account;

public interface AccountService {

	public Account createAccount(Account account);

	public Account getAccountDetailByAccountNumber(Long accountNumber);

	public List<Account> getAllAccounts();

	public Account deposit(Long accountNumber, double amount);

	public Account withdraw(Long accountNumber, double amount);
}
