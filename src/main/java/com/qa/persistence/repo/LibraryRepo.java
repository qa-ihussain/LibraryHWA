package com.qa.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.persistence.domain.Library;

@Repository
public interface LibraryRepo extends JpaRepository<Library, Long> {
	

	// CRUD FUNCTIONALITY


}