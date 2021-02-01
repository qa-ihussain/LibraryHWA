package com.qa.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.persistence.domain.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
	

}