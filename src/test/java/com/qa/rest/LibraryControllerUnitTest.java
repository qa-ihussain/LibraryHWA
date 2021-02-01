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
import com.qa.persistence.domain.Library;
import com.qa.persistence.dto.LibraryDTO;
import com.qa.services.LibraryService;

@SpringBootTest(classes = LibraryApplication.class)
public class LibraryControllerUnitTest {

	@Autowired
	private LibraryController controller;
	
	@MockBean
	private LibraryService service;
	
	private List<Library> bookList;
	private LibraryDTO libraryDTO;
	private Library testLibrary;
	private Long Id;
	private ModelMapper mapper = new ModelMapper();
	
	private LibraryDTO mapToDTO(Library model) {
		return this.mapper.map(model, LibraryDTO.class);
	}
	
	@BeforeEach
	void init() {
		this.Id = 1L;
		this.bookList = new ArrayList<>();
		this.testLibrary = new Library(Id, "The Midnight Library", "Matt Haig", "Science Fiction", 304, 2, null);
		this.libraryDTO = new LibraryDTO(Id,"The Midnight Library", "Matt Haig", "Science Fiction", 304, 2, null);
		
		this.bookList.add(testLibrary);
		this.libraryDTO = this.mapToDTO(testLibrary);
	}
	
	@Test
	public void createBookTest() {
		Mockito.when(this.service.createBook(testLibrary)).thenReturn(libraryDTO);
		
		assertThat(new ResponseEntity<LibraryDTO>(libraryDTO, HttpStatus.CREATED))
				.usingRecursiveComparison().isEqualTo(controller.createBook(testLibrary));
		
		Mockito.verify(this.service, Mockito.times(1)).createBook(testLibrary);
	}
	
	@Test
	public void readOneBookTest() {
		Mockito.when(this.service.readOneBook(Id)).thenReturn(libraryDTO);
		
		assertThat(ResponseEntity.ok(this.service.readOneBook(Id)))
				.usingRecursiveComparison().isEqualTo(controller.readBook(Id));
		
		Mockito.verify(this.service, Mockito.times(2)).readOneBook(Id);
	}
	
	@Test
	public void readAllTest() {
		Mockito.when(this.service.readAll()).thenReturn(bookList.stream().map
				(this::mapToDTO).collect(Collectors.toList()));
		
		assertThat(ResponseEntity.ok(this.service.readAll()))
				.usingRecursiveComparison().isEqualTo(controller.readAll());
		
		Mockito.verify(this.service, Mockito.times(2)).readAll();
	}
	
	@Test
	public void updateBookTest() {
		Library updatedLibrary = new Library(Id,"The Midnight Library", "Matt Haig", "Science Fiction", 304, 2, null);
		LibraryDTO updatedDTO = new LibraryDTO(Id, "The Midnight Library", "Matt Haig", "Science Fiction", 304, 1, null);
		
		Mockito.when(this.service.update(Id, updatedLibrary)).thenReturn(updatedDTO);
		
		assertThat(new ResponseEntity<>(updatedDTO, HttpStatus.ACCEPTED))
				.usingRecursiveComparison().isEqualTo
				(controller.updateLibrary(Id, updatedLibrary));
		
		Mockito.verify(this.service, Mockito.times(1)).update(Id, updatedLibrary);		
	}
	
	@Test
	public void deleteTest() {
		Mockito.when(this.service.delete(Id)).thenReturn(true);
		
		assertThat(new ResponseEntity<>(HttpStatus.NO_CONTENT))
				.usingRecursiveComparison().isEqualTo(controller.deleteBook(Id));
		
		Mockito.verify(this.service, Mockito.times(1)).delete(Id);
	}
	
	@Test
	public void deleteFailTest() {
		controller.deleteBook(Id);
		
		Mockito.verify(this.service, Mockito.times(1)).delete(Id);	
	}
	
}