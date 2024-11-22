package com.example.Mini_Project1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration

public class OpenAPIConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("My API").version("v1")
						.description("API documentation for my Spring Boot application"))
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Require bearer token globally
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme().type(SecurityScheme.Type.HTTP) // HTTP Authentication
								.scheme("bearer") // Use Bearer scheme for JWT
								.bearerFormat("JWT") // Optional: Indicates that JWT is used
								.description("Provide your Bearer token")));
		
	}
}
