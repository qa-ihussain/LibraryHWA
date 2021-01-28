package com.qa.services;

import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.persistence.domain.Library;
import com.qa.persistence.dto.LibraryDTO;
import com.qa.persistence.repo.LibraryRepo;
import com.qa.utils.MyBeanUtils;

@Service
public class LibraryService {

	private LibraryRepo repo;
	private ModelMapper mapper;

	@Autowired
	public LibraryService(LibraryRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private LibraryDTO mapToDTO(Library book) {
		return this.mapper.map(book, LibraryDTO.class);
	}

// ADD BOOK
	public LibraryDTO addBook(Library book) {
		Library saved = this.repo.save(book);
		return this.mapToDTO(saved);
	}

// READ BOOK(S)
	public LibraryDTO readOne(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
	}
	
	public List<LibraryDTO> getAllBooks() {
		
		List<Library> userlist = this.repo.findAll();
		List<LibraryDTO> userlistDTO = userlist.stream().map(this::mapToDTO).collect(Collectors.toList());
		
		return userlistDTO;
	}

// UPDATE LIBRARY

public LibraryDTO updateBook(Long id, Library user) {
		
		Library updatedUser = this.repo.findById(id).orElseThrow();
		MyBeanUtils.mergeNotNull(user, updatedUser);
		
		return this.mapToDTO(this.repo.save(updatedUser));
	}
	
// DELETE BOOK
	public boolean removeBook(Long id) {
		this.repo.deleteById(id);
		boolean exists = this.repo.existsById(id);
		return !exists;
	}

}