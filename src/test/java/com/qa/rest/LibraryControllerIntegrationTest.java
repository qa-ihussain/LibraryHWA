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
import com.qa.persistence.domain.Library;
import com.qa.persistence.dto.LibraryDTO;

@SpringBootTest(classes = LibraryApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "test")
public class LibraryControllerIntegrationTest {


	@Autowired
	private MockMvc mock;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ObjectMapper jsonifier;

	private LibraryDTO mapToDTO(Library model) {
		return this.mapper.map(model, LibraryDTO.class);
	}

	private final int TEST_ID = 1;

	@Test
	public void createBook() throws Exception {
		Library testLibrary = new Library();
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/library/addBook")
				.contentType(MediaType.APPLICATION_JSON).content(this.jsonifier.writeValueAsString(testLibrary))
				.accept(MediaType.APPLICATION_JSON);

		testLibrary.setId(3L);

		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(mapToDTO(testLibrary)));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();

		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	@Test
	public void readAll() throws Exception {
		List<LibraryDTO> bookList = new ArrayList<>();
		bookList.add(new LibraryDTO(1L, "The Midnight Library", "Matt Haig", "Science Fiction", 304, 2,
				new ArrayList<>()));
		bookList.add(new LibraryDTO(2L, "Extraterrestrial: The First Sign of Intelligent Life Beyond Earth", "Avi Loeb",
				"Science", 240, 1, new ArrayList<>()));
		bookList.add(new LibraryDTO(2L, "Kakegurui - Compulsive Gambler - Vol. 1", "Homura Kawamoto and Toru Naomura",
				"Manga", 240, 2, new ArrayList<>()));

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/library/readAll")
				.contentType(MediaType.APPLICATION_JSON)
				// .content(this.jsonifier.writeValueAsString(bookList))
				.accept(MediaType.APPLICATION_JSON);

		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.jsonifier.writeValueAsString(bookList));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();

		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	@Test
	public void readOneBook() throws Exception {
		Library testLibrary = new Library(1L, "The Midnight Library", "Matt Haig", "Science Fiction", 304, 2,
				null);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.GET, "/library/readBook/" + TEST_ID).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(testLibrary));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();

		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	@Test
	public void updateLibrary() throws Exception {
		LibraryDTO testLibraryDTO = new LibraryDTO(1L, "The Midnight Library", "Matt Haig", "Science Fiction", 304, 2,
				new ArrayList<>());

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.request(HttpMethod.PUT, "/library/updateBook/" + TEST_ID).contentType(MediaType.APPLICATION_JSON)
				.content(this.jsonifier.writeValueAsString(testLibraryDTO)).accept(MediaType.APPLICATION_JSON);

		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.jsonifier.writeValueAsString(testLibraryDTO));
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isAccepted();

		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

	@Test
	public void deleteBook() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.DELETE,
				"/library/deleteBook/" + TEST_ID);

		ResultMatcher matchStatus = MockMvcResultMatchers.status().isNoContent();

		this.mock.perform(mockRequest).andExpect(matchStatus);
	}

}