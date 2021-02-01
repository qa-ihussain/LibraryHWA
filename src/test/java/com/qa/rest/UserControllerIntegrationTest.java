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
import com.qa.LibraryApplication;
import com.qa.persistence.domain.Users;
import com.qa.persistence.dto.UsersDTO;

@SpringBootTest(classes = LibraryApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema-test.sql" ,"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles="test")
public class UserControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ObjectMapper jsonifier;
	
	private UsersDTO mapToDTO(Users model) {
		return this.mapper.map(model, UsersDTO.class);
	}
	
	private final int TEST_ID = 1;
	
	@Test
	public void createUser() throws Exception {
		Users testUsers = new Users(3L, "Tasha", "Lee", "tl@mail.com", "tlee", "password", null);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.POST, "/users/createUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(testUsers))
				.accept(MediaType.APPLICATION_JSON);
		
		testUsers.setId(3L);
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString
						(mapToDTO(testUsers)));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	@Test
	public void readAll() throws Exception {
		
		List<UsersDTO> userList = new ArrayList<>();
		userList.add(new UsersDTO(1L, "Iqra", "Hussain", "ih@mail.com", "iqra", "password"));
		userList.add(new UsersDTO(2L, "Abigail", "Jones", "aj@mail.com", "ajones", "password"));
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.GET, "/users/readAll")
				.contentType(MediaType.APPLICATION_JSON)
				//.content(this.jsonifier.writeValueAsString(trackList))
				.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(userList));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void readOneUser() throws Exception {
		UsersDTO userRead = new UsersDTO(1L, "Iqra", "Hussain", "ih@mail.com", "iqra", "password");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.GET, "/userss/readUser/" + TEST_ID)
				.contentType(MediaType.APPLICATION_JSON)
				// .content(this.jsonifier.writeValueAsString(updateUser))
				.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(userRead));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void updateTest() throws Exception {
		UsersDTO updateUser = new UsersDTO(1L, "Iqra", "Hussain", "ih@mail.com", "iqra", "newpass");
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.PUT, "/users/updateUser/" + TEST_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(updateUser))
				.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(updateUser));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isAccepted();
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);		
	}
	
	@Test
	public void deleteTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.DELETE, "/users/deleteUser/" + TEST_ID);
		
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isNoContent();
		
		this.mock.perform(mockRequest).andExpect(matchStatus);
	}
}