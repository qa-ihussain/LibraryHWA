package com.qa.persistence.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDTO {

	private Long id;
	private String bookTitle;
	private String author;
	private int pages;
	private Long ISBN10;
	private String ISBN13;
	private List<UserDTO> userlist;
	
	// maybe add boolean for availability and int for quantity of books available in
	// library?

}