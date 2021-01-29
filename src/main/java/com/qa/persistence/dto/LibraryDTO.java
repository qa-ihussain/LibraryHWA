package com.qa.persistence.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class LibraryDTO {

	private Long id;
	private String bookTitle;
	private String author;
	private int pages;
	private Long ISBN10;
	private String ISBN13;
	
	private List<UserDTO> user = new ArrayList<>();
	
	// maybe add boolean for availability and int for quantity of books available in
	// library?

}