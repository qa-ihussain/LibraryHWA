package com.qa.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String bookTitle;
	private String author;
	private int pages;
	private String genre;
	private int qty;
	private boolean availability; 
	
	@ManyToOne(targetEntity = User.class)
	private User users = null;

	public Library(Long id, @NotNull String bookTitle, String author, int pages, String genre, int qty,
			boolean availability) {
		super();
		this.id = id;
		this.bookTitle = bookTitle;
		this.author = author;
		this.pages = pages;
		this.genre = genre;
		this.qty = qty;
		this.availability = availability;
	}
	
	public Library(@NotNull String bookTitle, String author, int pages, String genre, int qty,
			boolean availability) {
		super();
		this.bookTitle = bookTitle;
		this.author = author;
		this.pages = pages;
		this.genre = genre;
		this.qty = qty;
		this.availability = availability;
	}
}