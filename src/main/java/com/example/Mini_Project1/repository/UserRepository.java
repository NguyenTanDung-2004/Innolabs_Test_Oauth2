package com.example.Mini_Project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;

import com.example.Mini_Project1.entity.User;


public interface UserRepository extends JpaRepository<User, String> {
	@Query(value = "select * from user where user.email = :email", nativeQuery = true)
	public User getUserByEmail(String email);
	
	@Query(value = "select * from user where user.role = 'staff'", nativeQuery = true)
	public List<User> getAllStaff();
}
