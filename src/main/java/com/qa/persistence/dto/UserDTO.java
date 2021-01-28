package com.qa.persistence.dto;

public class UserDTO {
	
	private Long Id;
	private String firstName;
	private String lastName;
	private int age;
	private String userName;
	private String password;
	
	public UserDTO() {
		super();
	}

	public UserDTO(Long id, String firstName, String lastName, int age, String userName, String password) {
		super();
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.userName = userName;
		this.password = password;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDTO [Id=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", userName=" + userName + ", password=" + password + "]";
	}
}
