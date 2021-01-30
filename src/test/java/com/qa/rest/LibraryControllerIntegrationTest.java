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
import com.qa.persistence.dto.LibraryDTO;
import com.qa.LibraryApplication;
import com.qa.persistence.domain.Library;

@SpringBootTest(classes = LibraryApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@Sql(scripts = { "classpath:schema-test.sql",
		"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles="test")

public class LibraryControllerIntegrationTest {

	private static final boolean FALSE = false;

	private static final boolean TRUE = false;

	@Autowired
	private MockMvc mock;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper mapper;

	private final Library TEST_BOOK_FROM_DB = new Library(1L, "The Midnight Library", "Matt Haig", 304, "0525559477", "978-0525559474", 2, TRUE);
	private final Library TEST_BOOK2_FROM_DB = new Library(2L, "Extraterrestrial: The First Sign of Intelligent Life Beyond Earth", "Avi Loeb", 240, "1529304822", "978-1529304824", 1, FALSE);
	private final Library TEST_BOOK3_FROM_DB = new Library(3L, "Kakegurui - Compulsive Gambler - Vol. 1", "Homura Kawamoto and Toru Naomura", 240, "0316562890", "978-0316562898", 2, TRUE);

	private LibraryDTO mapToDTO(Library library) {
		return this.modelMapper.map(library, LibraryDTO.class);
	}

	@Test
	void testAddBook() throws Exception {
		final Library NEW_BOOK = new Library("The Invisible Life of Addie LaRue", "V.E. Schwab", 560, "1785652508", "978-1785652509", 1, FALSE);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/library/addBook");
		mockRequest.contentType(MediaType.APPLICATION_JSON);
		mockRequest.content(this.mapper.writeValueAsString(NEW_BOOK));
		mockRequest.accept(MediaType.APPLICATION_JSON);

		final Library SAVED_BOOK = new Library(4L, NEW_BOOK.getBookTitle(), NEW_BOOK.getAuthor(), NEW_BOOK.getPages(), NEW_BOOK.getISBN10(), NEW_BOOK.getISBN13(), NEW_BOOK.getQty(), NEW_BOOK.isAvailability());

		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.mapper.writeValueAsString(this.mapToDTO(SAVED_BOOK)));
		this.mock.perform(mockRequest)
		.andExpect(matchStatus)
		.andExpect(matchContent);

	}

	@Test
	void testDeleteBook() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/library/deleteBook/" + this.TEST_BOOK_FROM_DB.getId()))
				.andExpect(status().isNoContent());
	}

	@Test
	void testGetAllBooks() throws Exception {
		List<LibraryDTO> bookList = new ArrayList<>();
		bookList.add(this.mapToDTO(TEST_BOOK_FROM_DB));
		bookList.add(this.mapToDTO(TEST_BOOK2_FROM_DB));
		bookList.add(this.mapToDTO(TEST_BOOK3_FROM_DB));

		String content = this.mock.perform(request(HttpMethod.GET, "/user/getAllBooks").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(bookList), content);
	}

//	@Test
//	void testUpdateLibrary() throws Exception {
//		LibraryDTO newLibrary = new LibraryDTO(TEST_BOOK_FROM_DB.getId(), "The Midnight Library", "Matt Haig", 304, 0525559477, "978-0525559474", 2, FALSE);
//		Library updatedLibrary = new Library(this.TEST_BOOK_FROM_DB.getId(), newBook.getBookTitle(), newBook.getAuthor(),
//				newBook.getPages(), newBook.getUserName(), newBook.getPassword());
//
//		String result = this.mock
//				.perform(request(HttpMethod.PUT, "/user/updateUser/?id=" + this.TEST_USER_FROM_DB.getId())
//						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
//						.content(this.mapper.writeValueAsString(newBook)))
//				.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();
//
//		assertEquals(this.mapper.writeValueAsString(this.mapToDTO(updatedUser)), result);
//	}

}