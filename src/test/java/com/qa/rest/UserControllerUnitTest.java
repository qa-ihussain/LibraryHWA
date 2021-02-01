package com.qa.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.LibraryApplication;
import com.qa.persistence.domain.Users;
import com.qa.persistence.dto.UsersDTO;
import com.qa.services.UserService;

@SpringBootTest(classes = LibraryApplication.class)

public class UserControllerUnitTest {
	
	@Autowired
	private UserController controller;
	
	@MockBean
	UserService service;
	
	private List<Users> userList;
	private UsersDTO userDTO;
	private Users userTest;
	private Long Id;
	private ModelMapper mapper = new ModelMapper();
	
	private UsersDTO mapToDTO(Users model) {
		return this.mapper.map(model, UsersDTO.class);
	}
	
	@BeforeEach
	void init() {
		this.Id = 1L;
		this.userList = new ArrayList<>();
		this.userDTO = new UsersDTO(Id, "Iqra", "Hussain", "ih@mail.com", "iqra", "password");
		this.userTest = new Users(Id,"Iqra", "Hussain", "ih@mail.com", "iqra", "password", null);
		
		this.userList.add(userTest);
		this.userDTO = this.mapToDTO(userTest);
	}
	
	@Test
	public void createUserTest() {
		Mockito.when(this.service.createUser(userTest)).thenReturn(userDTO);
		
		assertThat(new ResponseEntity<UsersDTO>(userDTO, HttpStatus.CREATED))
					.usingRecursiveComparison().isEqualTo(controller.createBook(userTest));
		
		Mockito.verify(this.service, Mockito.times(1)).createUser(userTest);
	}
	
	@Test
	public void readOneUserTest() {
		Mockito.when(this.service.readOneUser(Id)).thenReturn(userDTO);
		
		assertThat(ResponseEntity.ok(this.service.readOneUser(Id)))
				.usingRecursiveComparison().isEqualTo(controller.readBook(Id));
		
		Mockito.verify(this.service, Mockito.times(2)).readOneUser(Id);
	}
	
	@Test
	public void readAllTest() {
		Mockito.when(this.service.readAll()).thenReturn(userList.stream().map
				(this::mapToDTO).collect(Collectors.toList()));
		
		assertThat(ResponseEntity.ok(this.service.readAll()))
				.usingRecursiveComparison().isEqualTo(controller.readAll());
		
		Mockito.verify(this.service, Mockito.times(2)).readAll();
	}
	
	@Test
	public void updateUserTest() {
		Users updatedUser = new Users(1L, "Iqra", "Hussain", "ih@mail.com", "iqra", "password", null);
		UsersDTO updatedDTO = new UsersDTO(1L,"Iqra", "Hussain", "ih@mail.com", "iqra", "newpass");
		
		Mockito.when(this.service.update(Id, updatedUser)).thenReturn(updatedDTO);
		
		assertThat(new ResponseEntity<>(updatedDTO, HttpStatus.ACCEPTED))
					.usingRecursiveComparison()
					.isEqualTo(controller.updateUser(Id, updatedUser));
		
		Mockito.verify(this.service, Mockito.times(1)).update(Id, updatedUser);
	}

	@Test
	public void deleteTest() {
		Mockito.when(this.service.delete(Id)).thenReturn(true);
		
		assertThat(new ResponseEntity<>(HttpStatus.NO_CONTENT))
				.usingRecursiveComparison().isEqualTo(controller.deleteUser(Id));
		
		Mockito.verify(this.service, Mockito.times(1)).delete(Id);
	}	
	
	@Test
	public void deleteFailTest() {
		controller.deleteUser(Id);
		
		Mockito.verify(this.service, Mockito.times(1)).delete(Id);
	}

}