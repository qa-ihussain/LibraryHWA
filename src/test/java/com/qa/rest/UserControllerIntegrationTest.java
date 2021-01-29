package com.qa.rest;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.LibraryHobbyProjectApplication;
import com.qa.persistence.domain.User;
import com.qa.persistence.dto.UserDTO;

@SpringBootTest(classes = LibraryHobbyProjectApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "reg")
public class UserControllerIntegrationTest {
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ObjectMapper jsonifier;
	
	private UserDTO mapToDTO(User model) {
		return this.mapper.map(model, UserDTO.class);
	}
	
	private final int TEST_ID = 1;
	
// CREATE USER
	@Test
	public void createUser() throws Exception {
		User testUser = new User(3L, "Tasha", "Lee", 17, "tlee", "testing123", null);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.POST, "/user/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(testUser))
				.accept(MediaType.APPLICATION_JSON);
		
		testUser.setId(3L);
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
			 .json(this.jsonifier.writeValueAsString (mapToDTO(testUser)));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		
		this.mock.perform(mockRequest)
		.andExpect(matchStatus)
		.andExpect(matchContent);
	}
	
// READ USER(S)
	@Test
	public void getAllUsers() throws Exception {
		
		List<UserDTO> userList = new ArrayList<>();
		userList.add(new UserDTO(1L, "Claude", "Duvalier", 23, "cd52", "password"));
		userList.add(new UserDTO(2L, "Levi", "Ackerman", 27, "lackerman", "password"));
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.GET, "/user/readAll")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(userList))
				.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(userList));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	@Test
	public void readOneUser() throws Exception {
		User testUser = new User(1L, "Claude", "Duvalier", 23, "cd52", "password", null);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.GET, "/user/read/" + TEST_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(testUser));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void updateUser() throws Exception {
		UserDTO testUserDTO = new UserDTO(1L,  "Claude", "Duvalier", 23, "cd52", "test123");
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.PUT, "/user/update/" + TEST_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(testUserDTO))
				.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(testUserDTO));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isAccepted();
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void deleteUser() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.DELETE, "/user/delete/" + TEST_ID);
		
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isNoContent();
		
		this.mock.perform(mockRequest).andExpect(matchStatus);
	}
		
}