package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.User;
import com.security.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userservice;
	
	//------------get All Users-------------
	@GetMapping("/")
	public List<User> getAllUsers(){
		return userservice.getAllUsers();
	}
	
	//-----------get Single user------------
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return userservice.getUser(username);
		
		
	}
	
	@PostMapping("/useradd")
	public User addUser(@RequestBody User user) {
		return userservice.adduser(user);
		
	}
	
	

}
