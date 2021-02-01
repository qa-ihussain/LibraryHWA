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
import com.qa.persistence.domain.Library;
import com.qa.persistence.dto.LibraryDTO;
import com.qa.persistence.repo.LibraryRepo;
import com.qa.services.LibraryService;

@SpringBootTest(classes = LibraryApplication.class)
@Sql(scripts = { "classpath:schema-test.sql" ,"classpath:data-test.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)

public class LibraryServiceUnitTest {

	@Autowired
	private LibraryService service;

	@MockBean
	private LibraryRepo repoMock;

	private List<Library> bookList;
	private LibraryDTO libraryDTO;
	private Library libraryTest;
	private Long Id;

	@BeforeEach
	void init() {
		this.bookList = new ArrayList<>();
		this.libraryDTO = new LibraryDTO(Id,"The Midnight Library", "Matt Haig", "Science Fiction", 304, 2, null);
		this.libraryTest = new Library(Id, "The Midnight Library", "Matt Haig", "Science Fiction", 304, 2, null);
		this.Id = 1L;
		this.bookList.add(libraryTest);
	}

	@Test
	public void createBookTest() {
		Mockito.when(this.repoMock.save(Mockito.any(Library.class))).thenReturn(libraryTest);

		LibraryDTO result = this.service.createBook(libraryTest);

		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(libraryTest);

		Mockito.verify(this.repoMock, Mockito.times(1)).save(libraryTest);
	}

	@Test
	public void readOneBookTest() {
		Mockito.when(this.repoMock.findById(Id)).thenReturn(Optional.of(libraryTest));

		LibraryDTO result = this.service.readOneBook(Id);

		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(libraryDTO);

		Mockito.verify(this.repoMock, Mockito.times(1)).findById(Id);
	}

	@Test
	public void readAllBooksTest() {
		Mockito.when(this.repoMock.findAll()).thenReturn(bookList);

		List<LibraryDTO> result = this.service.readAll();
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(bookList);

		Mockito.verify(this.repoMock, Mockito.times(1)).findAll();
	}

	@Test
	public void updateLibraryTest() {
		libraryTest.setId(1L);
		Library updatedLibrary = new Library(1L, "The Midnight Library", "Matt Haig", "Science Fiction", 304, 2, null);
		updatedLibrary.setId(1L);

		LibraryDTO updatedDTO = new LibraryDTO(1L, "The Midnight Library", "Matt Haig", "Science Fiction", 304, 2, null);

		Mockito.when(this.repoMock.findById(Id)).thenReturn(Optional.of(libraryTest));
		Mockito.when(this.repoMock.save(Mockito.any(Library.class))).thenReturn(updatedLibrary);

		LibraryDTO result = this.service.update(Id, updatedLibrary);

		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(updatedDTO);
		Mockito.verify(this.repoMock, Mockito.times(1)).findById(Id);
	}

	@Test
	public void deleteBookTest() {
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