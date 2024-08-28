package com.security.service;

import com.security.model.Users;
import com.security.model.UsersPrinciple;
import com.security.repository.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users users = usersRepo.findByUserName(userName);

        if (users == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UsersPrinciple(users);
    }
}
