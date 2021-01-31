package com.qa.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.persistence.dto.UserDTO;
import com.qa.persistence.domain.User;
import com.qa.persistence.repo.UserRepo;
import com.qa.services.UserService;

@SpringBootTest
public class UserServiceUnitTest {

	@Autowired
	private UserService service;

	@MockBean
	private UserRepo repo;

	@MockBean
	private ModelMapper mapper;

	private List<User> userList;

	private User testUser;

	private User testUserWithID;

	private UserDTO userDTO;

	private final long ID = 1L;

	@BeforeEach
	void init() {
		this.userList = new ArrayList<>();
		this.userList.add(testUser);
		this.testUser = new User("Iqra", "Hussain", "iqra", "password");
		this.testUserWithID = new User(testUser.getFirstName(), testUser.getLastName(), testUser.getUserName(), testUser.getPassword());
		this.testUserWithID.setId(ID);
		this.userDTO = new ModelMapper().map(testUserWithID, UserDTO.class);
	}

	@Test
	void createUserTest() {
		UserDTO newUserDTO = new UserDTO(null, "Tasha", "Lee", "tlee", "password");

		when(this.mapper.map(newUserDTO, User.class)).thenReturn(testUser);
		when(this.repo.save(testUser)).thenReturn(testUserWithID);
		when(this.mapper.map(testUserWithID, UserDTO.class)).thenReturn(userDTO);

		assertThat(this.userDTO).isEqualTo(this.service.createUser(newUserDTO));

		verify(this.repo, times(1)).save(this.testUser);
	}

	@Test
	void deleteUserTest() {
		when(this.repo.existsById(ID)).thenReturn(true, false);

		assertThat(this.service.deleteUser(ID)).isTrue();

		verify(this.repo, times(1)).deleteById(ID);
		verify(this.repo, times(2)).existsById(ID);
	}

	@Test
	void findUserByIDTest() {
		when(this.repo.findById(this.ID)).thenReturn(Optional.of(this.testUserWithID));
		when(this.mapper.map(testUserWithID, UserDTO.class)).thenReturn(userDTO);

		assertThat(this.userDTO).isEqualTo(this.service.findUserByID(this.ID));

		verify(this.repo, times(1)).findById(this.ID);
	}

	@Test
	void readUserTest() {

		when(repo.findAll()).thenReturn(this.userList);
		when(this.mapper.map(testUserWithID, UserDTO.class)).thenReturn(userDTO);

		assertThat(this.service.getAllUsers().isEmpty()).isFalse();

		verify(repo, times(1)).findAll();
	}

	@Test
	void updateDucksTest() {
		// given
		final long ID = 1L;
		DuckDTO newDuck = new DuckDTO(null, "Daffy", "Black", "WB Studios");
		Duck duck = new Duck("Donald", "White", "Disney World");
		duck.setId(ID);
		Duck updatedDuck = new Duck(newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());
		updatedDuck.setId(ID);
		DuckDTO updatedDTO = new DuckDTO(ID, updatedDuck.getName(), updatedDuck.getColour(), updatedDuck.getHabitat());

		when(this.repo.findById(this.ID)).thenReturn(Optional.of(duck));
		// You NEED to configure a .equals() method in Duck.java for this to work
		when(this.repo.save(updatedDuck)).thenReturn(updatedDuck);
		when(this.mapper.map(updatedDuck, DuckDTO.class)).thenReturn(updatedDTO);

		assertThat(updatedDTO).isEqualTo(this.service.updateDuck(newDuck, this.ID));

		verify(this.repo, times(1)).findById(1L);
		verify(this.repo, times(1)).save(updatedDuck);
	}

}