package com.qa.rest;

import java.util.List;

import javax.websocket.server.PathParam;

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

import com.qa.persistence.dto.UserDTO;
import com.qa.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserService {

	private UserService service;

	@Autowired
	public UserService(UserService service) {
		super();
		this.service = service;
	}

	@PostMapping("/createUser")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
		return new ResponseEntity<>(this.service.createUser(user), HttpStatus.CREATED);
	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
		return this.service.deleteUser(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.findUserByID(id));
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(this.service.getUser());
	}

	@PutMapping("/updateUser")
	public ResponseEntity<UserDTO> updateUser(@PathParam("id") Long id, @RequestBody UserDTO user) {
		return new ResponseEntity<>(this.service.updateUser(user, id), HttpStatus.ACCEPTED);
	}

}