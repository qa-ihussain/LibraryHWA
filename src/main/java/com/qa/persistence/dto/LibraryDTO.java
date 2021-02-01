package com.qa.persistence.dto;

import java.util.List;

import com.qa.persistence.domain.Library;

public class LibraryDTO {

	private Long Id;
	private String bookTitle;
	private String author;
	private String genre;
	private int pages;
	private int qty;
	private List<Library> bookList;
	
	public LibraryDTO() {
		super();
	}

	public LibraryDTO(Long id, String bookTitle, String author, String genre, int pages, int qty,
			List<Library> bookList) {
		super();
		Id = id;
		this.bookTitle = bookTitle;
		this.author = author;
		this.genre = genre;
		this.pages = pages;
		this.qty = qty;
		this.bookList = bookList;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public List<Library> getBookList() {
		return bookList;
	}

	public void setBookList(List<Library> bookList) {
		this.bookList = bookList;
	}

	@Override
	public String toString() {
		return "LibraryDTO [Id=" + Id + ", bookTitle=" + bookTitle + ", author=" + author + ", genre=" + genre
				+ ", pages=" + pages + ", qty=" + qty + ", bookList=" + bookList
				+ "]";
	}
}
