package com.qa.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.persistence.domain.User;
import com.qa.persistence.dto.UserDTO;
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

	private UserDTO mapToDTO(User user) {
		return this.mapper.map(user, UserDTO.class);
	}

// CREATE USER
	public UserDTO addUser(User user) {
		User saved = this.repo.save(user);
		return this.mapToDTO(saved);
	}

// READ USER(S)
	public List<UserDTO> getAllUsers() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public UserDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}

// UPDATE USER
public UserDTO update(Long id, User user) {
		
		User updateUser = this.repo.findById(id).orElseThrow();
		MyBeanUtils.mergeNotNull(user, updateUser);
		
		return this.mapToDTO(this.repo.save(updateUser));
	}

// DELETE USER
	public boolean removeUser(Long id) {
		this.repo.deleteById(id);
		boolean exists = this.repo.existsById(id);
		return !exists;
	}

}
