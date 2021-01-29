package com.qa.rest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import com.qa.persistence.domain.Library;
import com.qa.persistence.dto.LibraryDTO;

@SpringBootTest(classes = LibraryHobbyProjectApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "reg")

public class LibraryControllerIntegrationTest {
		@Autowired
		private MockMvc mock;

		@Autowired
		private ModelMapper mapper;

		@Autowired
		private ObjectMapper jsonifier;
		
		private LibraryDTO mapToDTO(Library model) {
			return this.mapper.map(model,  LibraryDTO.class);
		}

		private final int BOOK_ID = 1;
		
		// CREATE
		@Test
		public void addBook() throws Exception {
			// STAGED RESOURCE // EXPECTATION
			LibraryDTO TEST_BOOK = new LibraryDTO(4L, "The Communist Manifesto", "Carl Marx and Friedrich Engels", 32, null, "979-8591606848", null);
			TEST_BOOK.setId(4L);

			// PREPARED REST REQUEST
			MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
					.request(HttpMethod.POST, "/library/addBook")
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.jsonifier.writeValueAsString(TEST_BOOK))
					.accept(MediaType.APPLICATION_JSON);
			
			// ASSERTION CHECKS
			ResultMatcher matchContent = MockMvcResultMatchers.content()
					.json(this.jsonifier.writeValueAsString(TEST_BOOK));
			ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();

			// PERFORM & ASSERT
			this.mock.perform(mockRequest)
			.andExpect(matchStatus)
			.andExpect(matchContent);
		}

		// READ
		@Test
		public void viewBook() throws Exception {
			// STAGED RESOURCE // EXPECTATION
			LibraryDTO TEST_BOOK = new LibraryDTO();
			

			// PREPARED REST REQUEST
			MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
					.request(HttpMethod.GET, "/library/viewBook" + BOOK_ID).contentType(MediaType.APPLICATION_JSON)
					.content(this.jsonifier.writeValueAsString(TEST_BOOK)).accept(MediaType.APPLICATION_JSON);

			// ASSERTION CHECKS
			ResultMatcher matchContent = MockMvcResultMatchers.content()
					.json(this.jsonifier.writeValueAsString(TEST_BOOK));
			ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();

			// PERFORM & ASSERT
			this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
		}
		
		@Test
		public void viewAll() throws Exception {
			
			List<LibraryDTO> bookList = new ArrayList<>();
			bookList.add(new LibraryDTO(1L, "The Midnight Library", "Matt Haig", 304, 0525559477, "978-0525559474"));
			bookList.add(new LibraryDTO(2L, "Extraterrestrial: The First Sign of Intelligent Life Beyond Earth", "Avi Loeb", 240, 1529304822, "978-1529304824"));
			bookList.add(new LibraryDTO(3L, "Kakegurui - Compulsive Gambler - Vol. 1', 'Homura Kawamoto and Toru Naomura', 240, 0316562890, '978-0316562898"));
			
			MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
					.request(HttpMethod.GET, "/library/viewAll")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
			
			ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.jsonifier.writeValueAsString(bookList));
			ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
			
			this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);		
		}
		
	// UPDATE
		@Test
		public void updateBook() throws Exception {
			// STAGED RESOURCE // EXPECTATION
			Library TEST_BOOK = new Library(TEST_BOOK, "Extraterrestrial: The First Sign of Intelligent Life Beyond Earth", "Avi Loeb", 240, 1529304822, "978-1529304824");
			
			// PREPARED REST REQUEST
			MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
					.request(HttpMethod.PUT, "/library/updateBook" + BOOK_ID).contentType(MediaType.APPLICATION_JSON)
					.content(this.jsonifier.writeValueAsString(TEST_BOOK)).accept(MediaType.APPLICATION_JSON);
			TEST_BOOK.setId(2L);

			
			// ASSERTION CHECKS
			ResultMatcher matchContent = MockMvcResultMatchers.content()
					.json(this.jsonifier.writeValueAsString(mapToDTO(TEST_BOOK)));
			ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();

			// PERFORM & ASSERT
			this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
		}
		

		// DELETE
		@Test
		public void removeBook() throws Exception {
			// PREPARED REST REQUEST
			MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
					"/library/removeBook" + BOOK_ID);

			// ASSERTION CHECKS
			ResultMatcher matchContent = MockMvcResultMatchers.content()
					.json(this.jsonifier.writeValueAsString(BOOK_ID));
			ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();

			// PERFORM & ASSERT
			this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);	
			}
	}

}
