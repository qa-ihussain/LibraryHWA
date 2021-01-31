package com.qa.persistence.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public final class UserDTO {
	
	private Long Id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;

	public UserDTO(Long id, String firstName, String lastName, String userName, String password) {
		super();
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}
}