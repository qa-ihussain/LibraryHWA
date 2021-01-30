package com.qa.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.persistence.dto.UserDTO;
import com.qa.persistence.domain.User;
import com.qa.services.UserService;

@SpringBootTest
class UserControllerUnitTest {

	@Autowired
	private UserController controller;

	@MockBean
	private UserService service;

	private List<User> userList;

	private UserDTO testUser;

	private User testUserWithID;

	private UserDTO userDTO;

	private final long id = 1L;

	private ModelMapper mapper = new ModelMapper();

	private UserDTO mapToDTO(User user) {
		return this.mapper.map(user, UserDTO.class);
	}

	@BeforeEach
	void init() {
		this.userList = new ArrayList<>();
		this.testUser = this.mapToDTO(new User(id, "Claude", "Duvalier", 23, "cd52", "password", null));

		this.testUserWithID = new User(id, testUser.getFirstName(), testUser.getLastName(), testUser.getAge(), testUser.getUserName(), testUser.getPassword(), null);
		this.testUserWithID.setId(id);

		this.userList.add(testUserWithID);
		this.userDTO = this.mapToDTO(testUserWithID);
	}

	@Test
	void createUserTest() {
		when(this.service.createUser(testUser)).thenReturn(this.userDTO);

		assertThat(new ResponseEntity<UserDTO>(this.userDTO, HttpStatus.CREATED))
				.isEqualTo(this.controller.createUser(testUser));

		verify(this.service, times(1)).createUser(this.testUser);
	}

	@Test
	void deleteUserTest() {
		this.controller.deleteUser(id);

		verify(this.service, times(1)).deleteUser(id);
	}

	@Test
	void findUserByIDTest() {
		when(this.service.findUserByID(this.id)).thenReturn(this.userDTO);

		assertThat(new ResponseEntity<UserDTO>(this.userDTO, HttpStatus.OK))
				.isEqualTo(this.controller.getUser(this.id));

		verify(this.service, times(1)).findUserByID(this.id);
	}

//	@Test
//	void getAllUsersTest() {
//
//		when(service.getUser()).thenReturn(this.userList.stream().map(this::mapToDTO).collect(Collectors.toList()));
//
//		assertThat(this.controller.getUser(id).getBody().isEmpty()).isFalse();
//
//		verify(service, times(1)).getUser();
//	}

	@Test
	void updateUserTest() {
		// given
		UserDTO newUser = new UserDTO(id, "Tasha", "Lee", 19, "tlee", "password");
		UserDTO updatedUser = new UserDTO(this.id, newUser.getFirstName(), newUser.getLastName(), newUser.getAge(), newUser.getUserName(), newUser.getPassword());

		when(this.service.updateUser(newUser, this.id)).thenReturn(updatedUser);

		assertThat(new ResponseEntity<UserDTO>(updatedUser, HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.updateUser(this.id, newUser));

		verify(this.service, times(1)).updateUser(newUser, this.id);
	}
}