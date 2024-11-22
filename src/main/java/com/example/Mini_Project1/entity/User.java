package com.example.Mini_Project1.entity;

import java.time.LocalDate;
import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String role;
	private String email;
	private String name;
	private String password;
	private LocalDate dob;
	private String currentToken;
	
	@OneToMany
	@JoinColumn(name = "author_id")
	private Set<Article> listArticles;
	
	private int flagDelete;
}
