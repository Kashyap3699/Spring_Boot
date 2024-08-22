package com.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banking.entity.Users;
import com.banking.repository.UsersRepository;
import com.banking.service.Impli.UsersServiceImplimentation;

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
		user.setFullName("John Doe");
		// Initialize other fields as needed
	}

	@Test
	void testCreateUser() {
		when(userRepo.save(any(Users.class))).thenReturn(user);

		Users createdUser = usersService.createUser(user);

		verify(userRepo, times(1)).save(user);
		assertNotNull(createdUser);
		assertEquals(user.getId(), createdUser.getId());
		assertEquals(user.getFullName(), createdUser.getFullName());
	}

	@Test
	void testGetUserById() {
		when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

		Users foundUser = usersService.getUserById(1L);

		verify(userRepo, times(1)).findById(1L);
		assertNotNull(foundUser);
		assertEquals(user.getId(), foundUser.getId());
		assertEquals(user.getFullName(), foundUser.getFullName());
	}

	@Test
	void testGetAllUsers() {
		when(userRepo.findAll()).thenReturn(Arrays.asList(user));

		List<Users> allUsers = usersService.getAllUsers();

		verify(userRepo, times(1)).findAll();
		assertNotNull(allUsers);
		assertEquals(1, allUsers.size());
		Users foundUser = allUsers.get(0);
		assertEquals(user.getId(), foundUser.getId());
		assertEquals(user.getFullName(), foundUser.getFullName());
	}

	@Test
	void testUpdateUserById() {
		when(userRepo.save(any(Users.class))).thenReturn(user);

		Users updatedUser = usersService.updateUserById(user, 1L);

		verify(userRepo, times(1)).save(user);
		assertNotNull(updatedUser);
		assertEquals(user.getId(), updatedUser.getId());
		assertEquals(user.getFullName(), updatedUser.getFullName());
	}

}
