package com.qa.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/library")
public class LibraryController {

	private LibraryService services;

	@Autowired
	public LibraryController(LibraryService services) {
		super();
		this.services = services;
	}

	@GetMapping("/readAll")
	public ResponseEntity<List<LibraryDTO>> readAll() {
		return ResponseEntity.ok(this.services.readAll());
	}

	@GetMapping("/readBook/{Id}")
	public ResponseEntity<LibraryDTO> readBook(@PathVariable("Id") Long Id) {
		return ResponseEntity.ok(this.services.readOneBook(Id));
	}

	@PostMapping("/addBook")
	public ResponseEntity<LibraryDTO> createBook(@RequestBody Library library) {
		return new ResponseEntity<LibraryDTO>(this.services.createBook(library), HttpStatus.CREATED);
	}

	@PutMapping("/updateBook/{Id}")
	public ResponseEntity<LibraryDTO> updateLibrary(@PathVariable("Id") Long Id, @RequestBody Library updatedLibrary) {
		return new ResponseEntity<>(this.services.update(Id, updatedLibrary), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteBook/{Id}")
	public ResponseEntity<LibraryDTO> deleteBook(@PathVariable Long Id) {
		return this.services.delete(Id) ? 
				new ResponseEntity<>(HttpStatus.NO_CONTENT) : 
					new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}