package com.banking.service;

import java.util.List;

import com.banking.entity.Users;

public interface UsersService {

	public Users createUser(Users users);

	public Users getUserById(Long id);

	public List<Users> getAllUsers();

	public Users updateUserById(Users users, Long id);


}
