package com.qa.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	private Long ISBN10;
	private String ISBN13;
	
//	@OneToMany(mappedBy = "pond")
	@ManyToOne(targetEntity = User.class)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<User> user = new ArrayList<>();
	
	public Library (Long id) {
		this.id = id;
	}
	
	public Library (String bookTitle, String author, int pages, Long ISBN10, String ISBN13) {
		this.bookTitle = bookTitle;
		this.author = author;
		this.pages = pages;
		this.ISBN10 = ISBN10;
		this.ISBN13 = ISBN13;
	}
}
