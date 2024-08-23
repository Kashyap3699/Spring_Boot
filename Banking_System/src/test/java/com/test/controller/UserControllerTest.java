package com.test.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banking.controller.UserController;
import com.banking.entity.Users;
import com.banking.service.UsersService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private Users user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new Users();
        user.setId(1L);
        user.setFullName("Kashyap Hainiya");
        user.setAddress("Morbi");
        user.setGender("Male");
    }

    @Test
    void testCreateUser() throws Exception {
        when(usersService.createUser(any(Users.class))).thenReturn(new ResponseEntity<>(user, HttpStatus.CREATED));

        mockMvc.perform(post("/users/create")
                .contentType("application/json")
                .content("{\"id\":1,\"fullName\":\"Kashyap Hainiya\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Kashyap Hainiya"));
    }



    @Test
    void testGetUserById() throws Exception {
        when(usersService.getUserById(anyLong())).thenReturn(new ResponseEntity<>(user,HttpStatus.FOUND));

        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Kashyap Hainiya"));
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        when(usersService.getUserById(anyLong())).thenReturn(new ResponseEntity<>(user,HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<Users> usersList = Collections.singletonList(user);
       when(usersService.getAllUsers()).thenReturn(new ResponseEntity<>(usersList,HttpStatus.OK));

        mockMvc.perform(get("/users/allUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fullName").value("Kashyap Hainiya"));
    }

    @Test
    void testGetAllUsersEmpty() throws Exception {
        List<Users> usersList = Collections.singletonList(user);
        when(usersService.getAllUsers()).thenReturn(new ResponseEntity<>(usersList,HttpStatus.NO_CONTENT));

        mockMvc.perform(get("/users/allUsers"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateUserById() throws Exception {
        when(usersService.updateUserById(any(Users.class), anyLong())).thenReturn(new ResponseEntity<>(user,HttpStatus.OK));

        mockMvc.perform(put("/users/{id}", 1)
                .contentType("application/json")
                .content("{\"id\":1,\"fullName\":\"Kashyap Hainiya\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Kashyap Hainiya"));
    }

    @Test
    void testUpdateUserByIdNotFound() throws Exception {
        when(usersService.updateUserById(any(Users.class), anyLong())).thenReturn(new ResponseEntity<>(user,HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/users/{id}", 1)
                .contentType("application/json")
                .content("{\"id\":1,\"fullName\":\"Kashyap Hainiya\"}"))
                .andExpect(status().isNotFound());
    }


}

