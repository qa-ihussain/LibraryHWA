package com.qa.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.persistence.domain.Users;
import com.qa.persistence.dto.UsersDTO;
import com.qa.persistence.repo.UserRepo;
import com.qa.utils.MyBeanUtils;

@Service
public class UserService {

	private UserRepo repo;
	private ModelMapper mapper;

	@Autowired
	public UserService(UserRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private UsersDTO mapToDTO(Users model) {
		return this.mapper.map(model, UsersDTO.class);
	}

	// Create
	public UsersDTO createUser(Users model) {
		return mapToDTO(this.repo.save(model));
	}

	// Read
	public UsersDTO readOneUser(Long Id) {
		return this.mapToDTO(this.repo.findById(Id).orElseThrow());
	}

	public List<UsersDTO> readAll() {
		List<Users> userLists = this.repo.findAll();
		List<UsersDTO> userListDTO = userLists.stream().map(this::mapToDTO).collect(Collectors.toList());
		return userListDTO;
	}

	// Update
	public UsersDTO update(Long Id, Users users) {
		Users updatedUser = this.repo.findById(Id).orElseThrow();
		MyBeanUtils.mergeNotNull(users, updatedUser);
		return this.mapToDTO(this.repo.save(updatedUser));
	}

	// Delete
	public boolean delete(Long Id) {
		this.repo.deleteById(Id);
		return !this.repo.existsById(Id);
	}
}