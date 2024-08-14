package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Account;

public interface BankRepository extends JpaRepository<Account, Long>{

}
