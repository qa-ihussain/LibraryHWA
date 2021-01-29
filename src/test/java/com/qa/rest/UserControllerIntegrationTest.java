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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.persistence.dto.UserDTO;
import com.qa.persistence.domain.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)

public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper mapper;

	private final User TEST_USER_FROM_DB = new User(1L, "Claude", "Duvalier", 23, "cd52", "password", null);

	private UserDTO mapToDTO(User user) {
		return this.modelMapper.map(user, UserDTO.class);
	}

	@Test
	void testCreateUser() throws Exception {
		final User NEW_USER = new User(null, "Tasha", "Lee", 17, "tlee", "password", null);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/user/createUser");
		mockRequest.contentType(MediaType.APPLICATION_JSON);
		mockRequest.content(this.mapper.writeValueAsString(NEW_USER));
		mockRequest.accept(MediaType.APPLICATION_JSON);

		final User SAVED_USER = new User(3L, NEW_USER.getFirstName(), NEW_USER.getLastName(), NEW_USER.getAge(), NEW_USER.getUserName(), NEW_USER.getPassword(), null);

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

		String content = this.mock.perform(request(HttpMethod.GET, "/user/getAllUsers").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(userList), content);
	}

	@Test
	void testUpdateUser() throws Exception {
		UserDTO newUser = new UserDTO(null, "Eren", "Yeager", 17, "eren", "password");
		User updatedUser = new User(this.TEST_USER_FROM_DB.getId(), newUser.getFirstName(), newUser.getLastName(),
				newUser.getAge(), newUser.getUserName(), newUser.getPassword(), null);

		String result = this.mock
				.perform(request(HttpMethod.PUT, "/duck/updateUser/?id=" + this.TEST_USER_FROM_DB.getId())
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(newUser)))
				.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(this.mapToDTO(updatedUser)), result);
	}

}