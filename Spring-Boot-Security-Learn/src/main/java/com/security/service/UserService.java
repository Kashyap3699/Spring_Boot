package com.security.service;

import com.security.model.Users;
import com.security.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepo usersRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    public Users register(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        Users save = usersRepo.save(users);
        return save;
    }
}
