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

import com.qa.persistence.domain.User;
import com.qa.persistence.dto.UserDTO;
import com.qa.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService service;

	@Autowired
	public UserController(UserService service) {
		super();
		this.service = service;
	}

// CREATE USER
	@PostMapping("/createUser")
	public UserDTO addUser(@RequestBody User user) {
		return this.service.addUser(user);
	}

// READ USER(S)
	@GetMapping("/readAll")
	public List<UserDTO> getAllUsers() {
		return this.service.getAllUsers();
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<UserDTO> readUser(@PathVariable("id") Long id) {
		return ResponseEntity.ok(this.service.readOne(id));
	}

// UPDATE USER
	@PutMapping("/update/{id}")
	public UserDTO updateUser(@PathParam("id") Long id, @RequestBody User user) {
		return this.service.update(id, user);
	}

// DELETE USER
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<UserDTO> removeUser(@PathVariable("id") Long id) {
		return this.service.removeUser(id) ? 
				new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}