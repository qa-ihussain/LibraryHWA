package com.qa.persistence.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@NotNull
	private String bookTitle;
	private String author;
	private String genre;

	@NotNull
	private int pages;
	private int qty;

	@ManyToOne
	private Users users;

	public Library() {
		super();
	}


	public Library(Long id, @NotNull String bookTitle, String author, String genre, @NotNull int pages, int qty,
			Users users) {
		super();
		Id = id;
		this.bookTitle = bookTitle;
		this.author = author;
		this.genre = genre;
		this.pages = pages;
		this.qty = qty;
		this.users = users;
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}


	@Override
	public String toString() {
		return "Library [Id=" + Id + ", bookTitle=" + bookTitle + ", author=" + author + ", genre=" + genre + ", pages="
				+ pages + ", qty=" + qty + ", Users=" + users + "]";
	}
	
}