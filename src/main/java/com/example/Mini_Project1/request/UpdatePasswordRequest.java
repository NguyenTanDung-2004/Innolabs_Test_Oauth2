package com.example.Mini_Project1.request;

import org.springframework.beans.factory.annotation.Value;

import com.example.Mini_Project1.validator.password_constraint.PasswordConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePasswordRequest {
	@NotNull(message = "Email must be not null")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
	private String email;
	
	@NotNull(message = "Password must be not null")
    @PasswordConstraint()
	private String newPassword;
	
	@NotNull(message = "Password must be not null")
	@Size(min = 8, max = 8)
	private String token;
}
