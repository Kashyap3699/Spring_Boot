package com.banking.service.Impli;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.entity.Users;
import com.banking.repository.UsersRepository;
import com.banking.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsersServiceImplimentation implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public ResponseEntity<Users> createUser(Users user) {
        try {
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (user.getFullName() == null || user.getFullName().isEmpty() || user.getAddress() == null
                    || user.getAddress().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            // Validate Account balance
            if (user.getGender() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Negative balance is not allowed
            }

            Users savedUser = usersRepository.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log and handle exception
            log.error("Error occurred while creating account: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Users> getUserById(Long id) {
        // TODO Auto-generated method stub
        try {
            Optional<Users> optionalUser = usersRepository.findById(id);
            if (optionalUser.isPresent()) {
                Users user = optionalUser.get();
                return new ResponseEntity<>(user, HttpStatus.FOUND);

            } else {
                log.info("User not found with id :: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            log.error("Error occurred while getting user {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public ResponseEntity<List<Users>> getAllUsers() {
        // TODO Auto-generated method stub
        try {
            List<Users> allUsers = usersRepository.findAll();

            if (allUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } else {
                return new ResponseEntity<>(allUsers, HttpStatus.OK);
            }

        } catch (Exception e) {
            // Log the exception
            log.error("Error occurred while fetching all users: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Users> updateUserById(Users users, Long id) {
        try {

            if (users != null) {
                users.setId(id);
                Users save = usersRepository.save(users);
                log.info("User with ID: {} updated successfully", id);
                return ResponseEntity.status(HttpStatus.OK).body(users);


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




