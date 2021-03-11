package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.User;

public interface IUserRepo extends JpaRepository<User,Integer> {
	User findByEmail(String username);
	
}
