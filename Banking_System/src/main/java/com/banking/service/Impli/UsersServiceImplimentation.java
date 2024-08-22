package com.banking.service.Impli;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.Users;
import com.banking.repository.UsersRepository;
import com.banking.service.UsersService;

@Service
public class UsersServiceImplimentation implements UsersService {

	@Autowired
	UsersRepository userRepo;

	@Override
	public Users createUser(Users user) {
		// TODO Auto-generated method stub
		Users saveUser = userRepo.save(user);
		return saveUser;
	}

	@Override
	public Users getUserById(Long id) {
		// TODO Auto-generated method stub
		Optional<Users> byId = userRepo.findById(id);
		return byId.orElse(null);
	}

	@Override
	public List<Users> getAllUsers() {
		// TODO Auto-generated method stub
		List<Users> allusers = userRepo.findAll();
		return allusers;
	}

	@Override
	public Users updateUserById(Users users, Long id) {
		// TODO Auto-generated method stub
		users.setId(id);
		return userRepo.save(users);

	}


}
