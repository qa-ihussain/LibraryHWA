package com.qa.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private Long Id;

	@NotNull
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	
	@OneToMany(mappedBy = "users")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Library> library = new ArrayList<>();
	
	
	public User(Long id, @NotNull String firstName, String lastName, String userName, String password) {
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}
	
	public User(@NotNull String firstName, String lastName, String userName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}
}