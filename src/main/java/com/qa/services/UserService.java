package com.qa.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.qa.persistence.dto.UserDTO;
import com.qa.exceptions.UserNotFoundException;
import com.qa.persistence.domain.User;
import com.qa.persistence.repo.UserRepo;
import com.qa.utils.MyBeanUtils;

@Service
public class UserService {

	private UserRepo repo;

	private ModelMapper mapper;

	@Autowired
	public UserService(UserRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	private UserDTO mapToDTO(User user) {
		return this.mapper.map(user, UserDTO.class);
	}

	private User mapFromDTO(UserDTO user) {
		return this.mapper.map(user, User.class);
	}

	public UserDTO createUser(UserDTO userDTO) {
		User toSave = this.mapFromDTO(userDTO);
		User saved = this.repo.save(toSave);
		return this.mapToDTO(saved);
	}

	public boolean deleteUser(Long id) {
		if (!this.repo.existsById(id)) {
			throw new UserNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public UserDTO findUserByID(Long id) {
		final User found = this.repo.findById(id)
				.orElseThrow(() -> {
					return new ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist");
				});
		return this.mapToDTO(found);
	}

	public List<UserDTO> getUser() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public UserDTO updateUser(UserDTO user, Long id) {
		User toUpdate = this.repo.findById(id).orElseThrow(UserNotFoundException::new);
		MyBeanUtils.mergeNotNull(user, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

}