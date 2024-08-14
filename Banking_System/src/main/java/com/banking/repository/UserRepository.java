package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
