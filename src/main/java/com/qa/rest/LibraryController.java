package com.qa.rest;

import java.util.List;

import javax.websocket.server.PathParam;

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

	@PostMapping("/addBook")
	public ResponseEntity<LibraryDTO> addBook(@RequestBody LibraryDTO library) {
		return new ResponseEntity<>(this.service.addBook(library), HttpStatus.CREATED);
	}

	@DeleteMapping("/deleteBook/{id}")
	public ResponseEntity<LibraryDTO> deleteBook(@PathVariable Long id) {
		return this.service.deleteBook(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/getBook/{id}")
	public ResponseEntity<LibraryDTO> getBook(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.findBookByID(id));
	}

	@GetMapping("/getAllBooks")
	public ResponseEntity<List<LibraryDTO>> getAllBooks() {
		return ResponseEntity.ok(this.service.readBooks());
	}

	@PutMapping("/updateLibrary")
	public ResponseEntity<LibraryDTO> updateLibrary(@PathParam("id") Long id, @RequestBody LibraryDTO library) {
		return new ResponseEntity<>(this.service.updateLibrary(library, id), HttpStatus.ACCEPTED);
	}

}