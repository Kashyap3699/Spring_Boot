package com.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banking.controller.AccountController;
import com.banking.entity.Account;
import com.banking.service.AccountService;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

	@Mock
	private AccountService accountService;

	@InjectMocks
	private AccountController accountController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Account account;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
		account = new Account();
		account.setAccountNumber(1L);
		account.setAccountBalance(5000.0);
	}

	@Test
	void testCreateAccount() throws Exception {
		when(accountService.createAccount(any(Account.class))).thenReturn(account);

		mockMvc.perform(post("/account/createAccount")
				.contentType("application/json")
				.content("{\"accountNumber\":1,\"accountBalance\":5000.0}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.accountNumber").value(1))
				.andExpect(jsonPath("$.accountBalance").value(5000.0));

	}

	@Test
	void testCreateAccountException() throws Exception {
		when(accountService.createAccount(any(Account.class))).thenThrow(new RuntimeException("Creation error"));

		mockMvc.perform(post("/account/createAccount")
				.contentType("application/json")
				.content("{\"accountNumber\":1,\"accountBalance\":5000.0}"))
				.andExpect(status().isInternalServerError());

	}

	@Test
	void testGetAccountByAccountNumber() throws Exception {
		when(accountService.getAccountDetailByAccountNumber(anyLong())).thenReturn(account);

		mockMvc.perform(get("/account/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accountNumber").value(1))
				.andExpect(jsonPath("$.accountBalance").value(5000.0));

	}

	@Test
	void testGetAccountByAccountNumberNotFound() throws Exception {
		when(accountService.getAccountDetailByAccountNumber(anyLong())).thenReturn(null);

		mockMvc.perform(get("/account/1"))
				.andExpect(status().isNotFound());

	}

	@Test
	void testGetAllAccounts() throws Exception {
		when(accountService.getAllAccounts()).thenReturn(Collections.singletonList(account));

		mockMvc.perform(get("/account/allAccount"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].accountNumber").value(1))
				.andExpect(jsonPath("$[0].accountBalance").value(5000.0));

	}

	@Test
	void testGetAllAccountsEmpty() throws Exception {
		when(accountService.getAllAccounts()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/account/allAccount"))
				.andExpect(status().isNotFound());

	}

	@Test
	void testDepositAmount() throws Exception {
		when(accountService.deposit(anyLong(), anyDouble())).thenReturn(account);

		mockMvc.perform(put("/account/deposit/1/500"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accountNumber").value(1))
				.andExpect(jsonPath("$.accountBalance").value(5000.0));

	}

	@Test
	void testDepositAmountInvalid() throws Exception {
		mockMvc.perform(put("/account/deposit/1/-500"))
				.andExpect(status().isBadRequest());

	}

	@Test
	void testDepositAmountNotFound() throws Exception {
		when(accountService.deposit(anyLong(), anyDouble()))
				.thenThrow(new RuntimeException("Account not found"));

		mockMvc.perform(put("/account/deposit/1/500"))
				.andExpect(status().isNotFound());

	}
	
	@Test
	void testWithdrawAmount() throws Exception {
		when(accountService.withdraw(anyLong(), anyDouble())).thenReturn(account);

		mockMvc.perform(put("/account/withdraw/1/500"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accountNumber").value(1))
				.andExpect(jsonPath("$.accountBalance").value(5000.0));

	}

	@Test
	void testWithdrawAmountInvalid() throws Exception {
		mockMvc.perform(put("/account/withdraw/1/-500"))
				.andExpect(status().isBadRequest());

	}

	@Test
	void testWithdrawAmountNotFound() throws Exception {
		when(accountService.withdraw(anyLong(), anyDouble()))
				.thenThrow(new RuntimeException("Account not found"));

		mockMvc.perform(put("/account/withdraw/1/500"))
				.andExpect(status().isNotFound());

	}
}
