package com.test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banking.entity.Users;
import com.banking.repository.UsersRepository;
import com.banking.service.Impli.UsersServiceImplimentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserServiceImpliTest {

    @Mock
    private UsersRepository userRepo;

    @InjectMocks
    private UsersServiceImplimentation usersService;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1L);
        user.setFullName("Kashyap Hainiya");
        user.setAddress("Morbi");
        user.setGender("Male");
        // Initialize other fields as needed
    }

    @Test
    void testCreateUser() {
        when(userRepo.save(any(Users.class))).thenReturn(user);

        ResponseEntity<Users> createdUser = usersService.createUser(user);

        assertEquals(HttpStatus.CREATED, createdUser.getStatusCode());
        assertNotNull(createdUser);
        assertEquals(user.getId(), Objects.requireNonNull(createdUser.getBody()).getId());
        assertEquals(user.getFullName(), createdUser.getBody().getFullName());
    }

    @Test
    void testCreatedUserIsNull() {

        ResponseEntity<Users> createdUser = usersService.createUser(null);
        assertNull(createdUser.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, createdUser.getStatusCode());

    }
    @Test
    void testCreatedUserMissingFields(){
        user.setGender(null);
        ResponseEntity<Users> createdUser = usersService.createUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, createdUser.getStatusCode());
    }


    @Test
    void testGetUserById() {
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        ResponseEntity<Users> foundUser = usersService.getUserById(1L);

        assertEquals(HttpStatus.FOUND, foundUser.getStatusCode());
        assertNotNull(foundUser);
        assertEquals(user.getId(), Objects.requireNonNull(foundUser.getBody()).getId());
        assertEquals(user.getFullName(), foundUser.getBody().getFullName());
    }

    @Test
    void testGetUserByIdNotFound(){
//        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Users> createdUser = usersService.getUserById(null);
        assertEquals(HttpStatus.NOT_FOUND,createdUser.getStatusCode());

    }

    @Test
    void testGetAllUsers() {
        when(userRepo.findAll()).thenReturn(Collections.singletonList(user));

        ResponseEntity<List<Users>> allUsers = usersService.getAllUsers();

        assertEquals(HttpStatus.OK, allUsers.getStatusCode());
        assertNotNull(allUsers);
        assertEquals(1, Objects.requireNonNull(allUsers.getBody()).size());
        Users foundUser = allUsers.getBody().get(0);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getFullName(), foundUser.getFullName());
    }

    @Test
    void testUpdateUserById() {
        when(userRepo.save(any(Users.class))).thenReturn(user);

        ResponseEntity<Users> updatedUser = usersService.updateUserById(user, 1L);

        assertEquals(HttpStatus.OK, updatedUser.getStatusCode());
        assertNotNull(updatedUser.getBody());
        assertEquals(user.getId(), Objects.requireNonNull(updatedUser.getBody()).getId());
        assertEquals(user.getFullName(), updatedUser.getBody().getFullName());
    }

}
