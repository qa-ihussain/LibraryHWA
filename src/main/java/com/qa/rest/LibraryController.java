package com.qa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.persistence.domain.Library;
import com.qa.persistence.dto.LibraryDTO;
import com.qa.services.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryController {

	private LibraryService service;

	@Autowired
	public LibraryController(LibraryService service) {
		super();
		this.service = service;
	}

// CREATE BOOK
	@PostMapping("/addBook")
	public LibraryDTO addBook(@RequestBody Library book) {
		return this.service.addBook(book);
	}

// READ BOOK(S)
	@GetMapping("/viewAll")
	public List<LibraryDTO> getAllBooks() {
		return this.service.getAllBooks();
	}
	
	@GetMapping("/viewBook/{id}")
	public ResponseEntity<LibraryDTO> readBook(@PathVariable("id") Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

// UPDATE BOOK
	@PutMapping("updateBook/{id}")
	public ResponseEntity<LibraryDTO> update(@PathVariable("id") Long id,  @RequestBody Library model) {
		return new ResponseEntity<>(this.service.updateBook(id, model), HttpStatus.ACCEPTED);
	}
	
// DELETE BOOK
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<LibraryDTO> removeBook(@PathVariable("id") Long id){
		return this.service.removeBook(id) ? 
				new ResponseEntity<>(HttpStatus.NO_CONTENT) :
				new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}