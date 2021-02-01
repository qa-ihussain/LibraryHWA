package com.qa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.qa.LibraryApplication;
import com.qa.persistence.domain.Users;
import com.qa.persistence.dto.UsersDTO;
import com.qa.persistence.repo.UserRepo;
import com.qa.services.UserService;

@SpringBootTest(classes = LibraryApplication.class)
@Sql(scripts = { "classpath:schema-test.sql" ,"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)

public class UserServiceUnitTest {

	@Autowired
	private UserService service;

	@MockBean
	private UserRepo repoMock;

	private List<Users> userList;
	private List<UsersDTO> userListDTO;
	private UsersDTO userDTO;
	private Users userTest;
	private Long Id;

	@BeforeEach
	void init() {
		this.userList = new ArrayList<>();
		this.userListDTO = new ArrayList<>();
		this.userDTO = new UsersDTO(1L, "Iqra", "Hussain", "ih@mail.com", "iqra", "password");
		this.userTest = new Users(1L, "Iqra", "Hussain", "ih@mail.com", "iqra", "password", null);
		this.userList.add(userTest);
		this.userListDTO.add(userDTO);
		this.Id = 1L;
	}

	@Test
	public void createUserTest() {
		Mockito.when(this.repoMock.save(Mockito.any(Users.class))).thenReturn(this.userTest);

		UsersDTO result = this.service.createUser(this.userTest);

		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(this.userDTO);

		Mockito.verify(this.repoMock, Mockito.times(1)).save(this.userTest);
	}

	@Test
	public void readAllTest() {

		Mockito.when(this.repoMock.findAll()).thenReturn(this.userList);

		List<UsersDTO> result = this.service.readAll();
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(this.userList);

		Mockito.verify(this.repoMock, Mockito.times(1)).findAll();
	}

	@Test
	public void readOneUserTest() {
		Mockito.when(this.repoMock.findById(this.Id)).thenReturn(Optional.of(this.userTest));

		UsersDTO result = this.service.readOneUser(this.Id);

		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(this.userDTO);

		Mockito.verify(this.repoMock, Mockito.times(1)).findById(this.Id);
	}

	@Test
	public void updateTest() {
		Users userUpdate = new Users(1L, "Iqra", "Hussain", "ih@mail.com", "iqra", "newpass", null);
		userUpdate.setId(1L);

		UsersDTO updatedUserDTO = new UsersDTO(1L, "Iqra", "Hussain", "ih@mail.com", "iqra", "newpass");
		this.userTest.setId(1L);

		Mockito.when(this.repoMock.findById(1L)).thenReturn(Optional.of(this.userTest));
		Mockito.when(this.repoMock.save(Mockito.any(Users.class))).thenReturn(userUpdate);

		UsersDTO result = this.service.update(1L, userUpdate);

		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(updatedUserDTO);
		Mockito.verify(this.repoMock, Mockito.times(1)).findById(1L);

	}

	@Test
	public void deleteUserTest() {
		Mockito.when(this.repoMock.existsById(Id)).thenReturn(false);
		Assertions.assertThat(this.service.delete(Id)).isTrue();
		Mockito.verify(this.repoMock, Mockito.times(1)).existsById(Id);
	}

	@Test
	public void deleteTestFail() {
		Mockito.when(this.repoMock.existsById(Id)).thenReturn(true);
		Assertions.assertThat(this.service.delete(Id)).isFalse();
		Mockito.verify(this.repoMock, Mockito.times(1)).existsById(Id);
	}
}