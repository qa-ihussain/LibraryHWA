package com.qa.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@NotNull
	private String firstName;
	private String lastName;
	private int age;
	private String userName;
	private String password;

//	@ManyToOne(targetEntity = Pond.class)
//	private Pond pond = null;
	@OneToMany(targetEntity = Library.class)
	private Library library = null;

	
public User(Long id, @NotNull String firstName, String lastName, int age, String userName, String password,
		Library library) {
	super();
	Id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.age = age;
	this.userName = userName;
	this.password = password;
	this.library = library;
}
	
	
	
	
	
}