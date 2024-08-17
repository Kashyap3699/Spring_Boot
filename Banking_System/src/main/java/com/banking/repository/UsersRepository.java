package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
