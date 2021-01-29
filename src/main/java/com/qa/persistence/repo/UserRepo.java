package com.qa.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.persistence.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	

	List<User> findByUserName(String userName);

	// CRUD FUNCTIONALITY


}