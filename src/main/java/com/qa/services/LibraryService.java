package com.qa.services;

import java.util.List;
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

	private LibraryDTO mapToDTO(Library model) {
		return this.mapper.map(model, LibraryDTO.class);
	}

	// Create
	public LibraryDTO createBook(Library model) {
		return mapToDTO(this.repo.save(model));
	}

	// Read
	public LibraryDTO readOneBook(Long Id) {
		return this.mapToDTO(this.repo.findById(Id).orElseThrow());
	}

	public List<LibraryDTO> readAll() {
		List<Library> bookLists = this.repo.findAll();
		List<LibraryDTO> bookListDTO = bookLists.stream().map(this::mapToDTO).collect(Collectors.toList());
		return bookListDTO;
	}

	// Update
	public LibraryDTO update(Long Id, Library library) {
		Library updatedLibrary = this.repo.findById(Id).orElseThrow();
		MyBeanUtils.mergeNotNull(library, updatedLibrary);
		return this.mapToDTO(this.repo.save(updatedLibrary));
	}

	// Delete
	public boolean delete(Long Id) {
		this.repo.deleteById(Id);
		return !this.repo.existsById(Id);
	}
}