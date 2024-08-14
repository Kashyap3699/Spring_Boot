package com.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.security.model.User;

@Service
public class UserService {

	List<User> list = new ArrayList<>();

	public UserService() {

		list.add(new User("abc", "123", "abc@gmail.com"));
		list.add(new User("xyz", "456", "xyz@gmail.com"));

	}

	// get all users
	public List<User> getAllUsers() {
		return list;
	}

	// get single user
	public User getUser(String userName) {
		return this.list.stream()
				.filter(user -> user.getUserName().equals(userName))
				.findAny()
				.orElse(null);
	}

	// add new user
	public User adduser(User user) {
		list.add(user);
		return user;

	}
}
