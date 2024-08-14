package com.banking.service;

import java.util.List;

import com.banking.entity.User;

public interface UserService {

	public User createUser(User user);

	public User getUserById(Long id);

	public List<User> getAllUsers();

	public void updateUserById(User user, Long id);

	public void deleteUserById(User user, Long id);

}
