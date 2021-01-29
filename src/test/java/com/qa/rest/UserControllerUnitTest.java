package com.qa.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.qa.persistence.domain.User;
import com.qa.persistence.dto.UserDTO;
import com.qa.rest.UserController;
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

	private final Long id = 1L;

	private ModelMapper mapper = new ModelMapper();

	private UserDTO mapToDTO(User user) {
		return this.mapper.map(user, UserDTO.class);
	}

	@BeforeEach
	void init() {
		this.userList = new ArrayList<>();
		this.userList = this.mapToDTO(new User(3L, "Tasha", "Lee", 17, "tlee", "testing123"));
		
		this.testDuckWithID = new Duck(testDuck.getName(), testDuck.getColour(), testDuck.getHabitat());
		this.testDuckWithID.setId(id);

		this.duckList.add(testDuckWithID);
		this.duckDTO = this.mapToDTO(testDuckWithID);
	}

	@Test
	void createDuckTest() {
		when(this.service.createDuck(testDuck)).thenReturn(this.duckDTO);

		assertThat(new ResponseEntity<DuckDTO>(this.duckDTO, HttpStatus.CREATED))
				.isEqualTo(this.controller.createDuck(testDuck));

		verify(this.service, times(1)).createDuck(this.testDuck);
	}

	@Test
	void deleteDuckTest() {
		this.controller.deleteDuck(id);

		verify(this.service, times(1)).deleteDuck(id);
	}

	@Test
	void findDuckByIDTest() {
		when(this.service.findDuckByID(this.id)).thenReturn(this.duckDTO);

		assertThat(new ResponseEntity<DuckDTO>(this.duckDTO, HttpStatus.OK))
				.isEqualTo(this.controller.getDuck(this.id));

		verify(this.service, times(1)).findDuckByID(this.id);
	}

	@Test
	void getAllDucksTest() {

		when(service.readDucks()).thenReturn(this.duckList.stream().map(this::mapToDTO).collect(Collectors.toList()));

		assertThat(this.controller.getAllDucks().getBody().isEmpty()).isFalse();

		verify(service, times(1)).readDucks();
	}

	@Test
	void updateDucksTest() {
		// given
		DuckDTO newDuck = new DuckDTO(null, "Sir Duckington esq.", "Blue", "Duckington Manor");
		DuckDTO updatedDuck = new DuckDTO(this.id, newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());

		when(this.service.updateDuck(newDuck, this.id)).thenReturn(updatedDuck);

		assertThat(new ResponseEntity<DuckDTO>(updatedDuck, HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.updateDuck(this.id, newDuck));

		verify(this.service, times(1)).updateDuck(newDuck, this.id);
	}

}