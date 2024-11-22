package com.example.Mini_Project1.mapper;

import org.mapstruct.Mapper;  
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.Mini_Project1.entity.User;
import com.example.Mini_Project1.request.CreateStaffRequest;
import com.example.Mini_Project1.request.SignUpRequest;
import com.example.Mini_Project1.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(target = "password", ignore = true)
	public User signUpRequestToUser(SignUpRequest signUpRequest);
	
	@Mapping(target = "password", ignore = true)
	public User createStaffToUser(CreateStaffRequest createStaffRequest);
	
	public UserResponse UserToResponseUser(User user);

}
