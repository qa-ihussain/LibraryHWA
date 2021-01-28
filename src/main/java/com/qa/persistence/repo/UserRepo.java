package com.qa.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.persistence.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	

	// CRUD FUNCTIONALITY


}