package com.security.repository;

import com.security.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Integer> {

    Users findByUserName(String username);
}
