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

import com.qa.persistence.domain.Users;
import com.qa.persistence.dto.UsersDTO;
import com.qa.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

	private UserService services;

	@Autowired
	public UserController(UserService services) {
		super();
		this.services = services;
	}

	@GetMapping("/readAll")
	public ResponseEntity<List<UsersDTO>> readAll() {
		return ResponseEntity.ok(this.services.readAll());
	}

	@GetMapping("/readUser/{Id}")
	public ResponseEntity<UsersDTO> readBook(@PathVariable("Id") Long Id) {
		return ResponseEntity.ok(this.services.readOneUser(Id));
	}

	@PostMapping("/addUser")
	public ResponseEntity<UsersDTO> createBook(@RequestBody Users users) {
		return new ResponseEntity<UsersDTO>(this.services.createUser(users), HttpStatus.CREATED);
	}

	@PutMapping("/updateUser/{Id}")
	public ResponseEntity<UsersDTO> updateUser(@PathVariable("Id") Long Id, @RequestBody Users updatedUser) {
		return new ResponseEntity<>(this.services.update(Id, updatedUser), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteUser/{Id}")
	public ResponseEntity<UsersDTO> deleteUser(@PathVariable Long Id) {
		return this.services.delete(Id) ? 
				new ResponseEntity<>(HttpStatus.NO_CONTENT) : 
					new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}