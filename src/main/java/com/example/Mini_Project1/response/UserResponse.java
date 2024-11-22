package com.example.Mini_Project1.response;

import java.time.LocalDate;

import com.example.Mini_Project1.deserializer.LocalDateDeserializer;
import com.example.Mini_Project1.request.CreateStaffRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class UserResponse {
	private String id;
	private String name;
	private LocalDate dob;
	private String email;
}
