package com.test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.banking.entity.Account;
import com.banking.repository.AccountRepository;
import com.banking.service.Impli.AccountServiceImplimentation;

@ExtendWith(MockitoExtension.class)
class AccountServiceImpliTest {

	@Mock
	private AccountRepository repo;

	@InjectMocks
	private AccountServiceImplimentation accountService;

	private Account account;

	@BeforeEach
	void setUp() {
		account = new Account();
		account.setAccountNumber(1L);
		account.setAccountBalance(1000.0);
		account.setAccountType("Saving");
	}

	@Test
	void testCreateAccount() {
		when(repo.save(any(Account.class))).thenReturn(account);

		ResponseEntity<Account> createdAccount = accountService.createAccount(account);

		assertEquals(HttpStatus.CREATED,createdAccount.getStatusCode());
		assertNotNull(createdAccount);
		assertEquals(account.getAccountBalance(), Objects.requireNonNull(createdAccount.getBody()).getAccountBalance());
	}

	@Test
	void testGetAccountDetailByAccountNumber() {
		when(repo.findById(anyLong())).thenReturn(Optional.of(account));

		ResponseEntity<Account> fetchedAccount = accountService.getAccountDetailByAccountNumber(1L);

		assertEquals(HttpStatus.FOUND,fetchedAccount.getStatusCode());
		assertNotNull(fetchedAccount);
		assertEquals(1L, Objects.requireNonNull(fetchedAccount.getBody()).getAccountNumber());
	}

	@Test
	void testGetAllAccounts() {
		when(repo.findAll()).thenReturn(Collections.singletonList(account));

		ResponseEntity<List<Account>> accounts = accountService.getAllAccounts();

		assertEquals(HttpStatus.OK,accounts.getStatusCode());
		assertNotNull(accounts);
		assertEquals(1, Objects.requireNonNull(accounts.getBody()).size());
		assertEquals(1000.0, accounts.getBody().get(0).getAccountBalance());
	}

	@Test
	void testDeposit() {
		when(repo.findById(anyLong())).thenReturn(Optional.of(account));
		when(repo.save(any(Account.class))).thenReturn(account);

		ResponseEntity<Account> updatedAccount = accountService.deposit(1L, 500.0);

		assertNotNull(updatedAccount);
		assertEquals(1500.0, Objects.requireNonNull(updatedAccount.getBody()).getAccountBalance());
	}

	@Test
	void testDepositAccountNotFound() {
		when(repo.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<Account> updatedAccount = accountService.deposit(1L, 500.0);
		assertEquals(HttpStatus.NOT_FOUND,updatedAccount.getStatusCode());
	}

	@Test
	void testWithdraw() {
		// Add implementation if needed
		when(repo.findById(anyLong())).thenReturn(Optional.of(account));
		when(repo.save(any(Account.class))).thenReturn(account);

		ResponseEntity<Account> updatedAccount = accountService.withdraw(1L, 500.0);

		assertNotNull(updatedAccount);
		assertEquals(500.0, Objects.requireNonNull(updatedAccount.getBody()).getAccountBalance());
	}
	
	@Test
	void testWithdrawAccountNotFound() {
		when(repo.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<Account> updatedAccount = accountService.deposit(1L, 500.0);
		assertNull(updatedAccount.getBody());
		assertEquals(HttpStatus.NOT_FOUND,updatedAccount.getStatusCode());
	}


	@Test
	void testDeductAmountFromAccounts() {
		Account deductAmount = new Account();
		deductAmount.setAccountNumber(2L);
		deductAmount.setAccountBalance(100.0);

		when(repo.findByAccountBalanceGreaterThan(0)).thenReturn(List.of(account, deductAmount));
		when(repo.save(any(Account.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

		accountService.deductAmountFromAccounts();

		assertEquals(800.0, account.getAccountBalance());
		assertEquals(0.0, deductAmount.getAccountBalance());

	}
}
