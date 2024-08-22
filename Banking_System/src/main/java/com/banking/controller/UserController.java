package com.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.entity.Users;
import com.banking.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	UsersService usersservice;

	@PostMapping("/createUser")
	public ResponseEntity<Users> createUsers(@RequestBody Users users) {
		try {
			Users save = usersservice.createUser(users);
			log.info("User created successfully");
			return ResponseEntity.status(HttpStatus.CREATED).body(save);
		} catch (Exception e) {
			log.error("Error Creating user: ", e.getMessage(), e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable Long id) {
		Users userById = usersservice.getUserById(id);
		if (userById == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(userById);
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<Users>> getAllUsers() {
		List<Users> allUsers = usersservice.getAllUsers();
		if (allUsers.isEmpty()) { // Check if the list is empty
			log.warn("No users found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		log.info("Successfully retrieved {} users", allUsers.size());
		return ResponseEntity.status(HttpStatus.OK).body(allUsers);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Users> updateUserById(@RequestBody Users users, @PathVariable Long id) {
		try {
			Users updatedUser = usersservice.updateUserById(users, id);
			if (updatedUser != null) {
				log.info("User with ID: {} updated successfully", id);
				return ResponseEntity.status(HttpStatus.OK).body(updatedUser);

			} else {
				log.warn("User with ID: {} not found", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			log.error("Error updating user with ID: {}. Error message: {}", id, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

}
