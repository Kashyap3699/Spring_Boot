package com.banking.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.banking.entity.Users;

public interface UsersService {

	public ResponseEntity<Users> createUser(Users users);

	public ResponseEntity<Users> getUserById(Long id);

	public ResponseEntity<List<Users>> getAllUsers();

	public ResponseEntity<Users> updateUserById(Users users, Long id);


}
