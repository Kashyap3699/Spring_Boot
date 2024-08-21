package com.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
	}

	@Test
	void testCreateAccount() {
		when(repo.save(any(Account.class))).thenReturn(account);

		Account createdAccount = accountService.createAccount(account);

		assertNotNull(createdAccount);
		assertEquals(1000.0, createdAccount.getAccountBalance());
	}

	@Test
	void testGetAccountDetailByAccountNumber() {
		when(repo.findById(anyLong())).thenReturn(Optional.of(account));

		Account fetchedAccount = accountService.getAccountDetailByAccountNumber(1L);

		assertNotNull(fetchedAccount);
		assertEquals(1L, fetchedAccount.getAccountNumber());
	}

	@Test
	void testGetAllAccounts() {
		when(repo.findAll()).thenReturn(Collections.singletonList(account));

		List<Account> accounts = accountService.getAllAccounts();

		assertNotNull(accounts);
		assertEquals(1, accounts.size());
		assertEquals(1000.0, accounts.get(0).getAccountBalance());
	}

	@Test
	void testDeposit() {
		when(repo.findById(anyLong())).thenReturn(Optional.of(account));
		when(repo.save(any(Account.class))).thenReturn(account);

		Account updatedAccount = accountService.deposit(1L, 500.0);

		assertNotNull(updatedAccount);
		assertEquals(1500.0, updatedAccount.getAccountBalance());
	}

	@Test
	void testDepositAccountNotFound() {
		when(repo.findById(anyLong())).thenReturn(Optional.empty());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			accountService.deposit(1L, 500.0);
		});

		assertEquals("Account is not available", exception.getMessage());
	}

	@Test
	void testWithdraw() {
		// Add implementation if needed
	}

	@Test
	void testDeductAmountFromAccounts() {
		Account lowBalanceAccount = new Account();
		lowBalanceAccount.setAccountNumber(2L);
		lowBalanceAccount.setAccountBalance(100.0);

		when(repo.findByAccountBalanceGreaterThan(0)).thenReturn(List.of(account, lowBalanceAccount));
		when(repo.save(any(Account.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

		accountService.deductAmountFromAccounts();

		assertEquals(800.0, account.getAccountBalance());
		assertEquals(0.0, lowBalanceAccount.getAccountBalance());

		verify(repo, times(2)).save(any(Account.class));
	}
}
