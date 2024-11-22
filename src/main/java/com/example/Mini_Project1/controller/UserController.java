package com.example.Mini_Project1.controller;

import java.util.Set;

import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Mini_Project1.request.CreateStaffRequest;
import com.example.Mini_Project1.request.LoginRequest;
import com.example.Mini_Project1.request.SignUpRequest;
import com.example.Mini_Project1.request.UpdatePasswordRequest;
import com.example.Mini_Project1.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
	@Autowired
	private UserService userService;
	

	@PostMapping("/signup")
	public ResponseEntity signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		return userService.signUp(signUpRequest);
	}

	@PostMapping("/login")
	public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest);
	}

	@GetMapping("/getTokenResetPassword")
	public ResponseEntity getTokenResetPassword(
			@Valid @RequestParam @NotNull @Email String email) {
		return userService.getTokenResetPassword(email);
	}

	@PutMapping("/forgotPassword")
	public ResponseEntity updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
		return userService.updatePassword(updatePasswordRequest);
	}

	@PostMapping("/createStaff")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_admin')")
	public ResponseEntity createStaff(@Valid @RequestBody CreateStaffRequest createStaffRequest) {
		return userService.createStaff(createStaffRequest);
	}

	@GetMapping("/getAllStaff")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_admin')")
	public ResponseEntity getAllStaff() {
		return userService.getAllStaff();
	}

	@DeleteMapping("/deleteStaff")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_admin')")
	public ResponseEntity deleteStaff(@Valid @NotNull @UUID @RequestParam(name = "staffId") String staffId) {
		return userService.deleteStaff(staffId);
	}
	
	
	@GetMapping("/getUserInfo")
	public ResponseEntity getUserInfo(@Valid @NotNull @UUID @RequestParam(name = "userId") String userId) {
		return userService.getUserInfo(userId);
	}

}
