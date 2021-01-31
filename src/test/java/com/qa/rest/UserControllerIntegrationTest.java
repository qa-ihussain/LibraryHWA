package com.qa.rest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.persistence.dto.UserDTO;
import com.qa.LibraryApplication;
import com.qa.persistence.domain.User;

@SpringBootTest(classes = LibraryApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles="test")

public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper mapper;

	private final User TEST_USER_FROM_DB = new User(1L, "Iqra", "Hussain", "iqra", "password");
	private final User TEST_USER2_FROM_DB = new User(2L, "Abigail", "Jones", "ajones", "password");

	private UserDTO mapToDTO(User user) {
		return this.modelMapper.map(user, UserDTO.class);
	}

	@Test
	void testCreateUser() throws Exception {
		final User NEW_USER = new User(3L, "Tasha", "Lee", "tlee", "password");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/user/createUser");
		mockRequest.contentType(MediaType.APPLICATION_JSON);
		mockRequest.content(this.mapper.writeValueAsString(NEW_USER));
		mockRequest.accept(MediaType.APPLICATION_JSON);

		final User SAVED_USER = new User(3L, NEW_USER.getFirstName(), NEW_USER.getLastName(), NEW_USER.getUserName(), NEW_USER.getPassword());

		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.mapper.writeValueAsString(this.mapToDTO(SAVED_USER)));
		this.mock.perform(mockRequest)
		.andExpect(matchStatus)
		.andExpect(matchContent);

	}

	@Test
	void testDeleteUser() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/user/deleteUser/" + this.TEST_USER_FROM_DB.getId()))
				.andExpect(status().isNoContent());
	}

	@Test
	void testGetAllUsers() throws Exception {
		List<UserDTO> userList = new ArrayList<>();
		userList.add(this.mapToDTO(TEST_USER_FROM_DB));
		userList.add(this.mapToDTO(TEST_USER2_FROM_DB ));

		String content = this.mock.perform(request(HttpMethod.GET, "/user/getAllUsers").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(userList), content);
	}

	@Test
	void testUpdateUser() throws Exception {
		UserDTO newUser = new UserDTO(TEST_USER_FROM_DB.getId(), "Iqra", "Hussain", "iqra", "newpass");
		User updatedUser = new User(this.TEST_USER_FROM_DB.getId(), newUser.getFirstName(), newUser.getLastName(),
				newUser.getUserName(), newUser.getPassword());

		String result = this.mock
				.perform(request(HttpMethod.PUT, "/user/updateUser/?id=" + this.TEST_USER_FROM_DB.getId())
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(newUser)))
				.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(this.mapToDTO(updatedUser)), result);
	}

}