package com.qa.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.persistence.dto.LibraryDTO;
import com.qa.exceptions.BookNotFoundException;
import com.qa.persistence.domain.Library;
import com.qa.persistence.repo.LibraryRepo;

@Service
public class LibraryService {

	private LibraryRepo repo;

	private ModelMapper mapper;

	@Autowired
	public LibraryService(LibraryRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	private LibraryDTO mapToDTO(Library library) {
		return this.mapper.map(library, LibraryDTO.class);
	}

	private Library mapFromDTO(LibraryDTO library) {
		return this.mapper.map(library, Library.class);
	}

	public LibraryDTO addBook(LibraryDTO library) {
		Library toSave = this.mapFromDTO(library);
		Library saved = this.repo.save(toSave);
		return this.mapToDTO(saved);
	}

	public boolean deleteBook(Long id) {
		if (!this.repo.existsById(id)) {
			throw new BookNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public LibraryDTO findBookByID(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(BookNotFoundException::new));
	}

	public List<LibraryDTO> readBooks() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public LibraryDTO updateLibrary(LibraryDTO library, Long id) {
		Library toUpdate = this.repo.findById(id).orElseThrow(BookNotFoundException::new);
		toUpdate.setBookTitle(library.getBookTitle());
		return this.mapToDTO(this.repo.save(toUpdate));
	}

}