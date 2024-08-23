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
    AccountService accountService;

    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable Long accountNumber) {

        return accountService.getAccountDetailByAccountNumber(accountNumber);
    }

    @GetMapping("/allAccount")
    public ResponseEntity<List<Account>> getAllAccount() {
        return accountService.getAllAccounts();
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    public ResponseEntity<Account> depositAmount(@PathVariable("accountNumber") Long accountNumber,
                                                 @PathVariable("amount") double amount) {

        return accountService.deposit(accountNumber, amount);
    }

    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public ResponseEntity<Account> withdrawAmount(@PathVariable("accountNumber") Long accountNumber,
                                                  @PathVariable("amount") double amount) {

        return accountService.withdraw(accountNumber, amount);

    }
}
