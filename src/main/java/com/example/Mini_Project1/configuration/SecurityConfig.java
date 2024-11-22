package com.example.Mini_Project1.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import com.example.Mini_Project1.utils.JwtTokenUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	private final String[] GetPublicEnpoints = { "/swagger-ui/**", "/v3/api-docs*/**", "/user/getTokenResetPassword",
			"/article/get" };

	private final String[] PostPublicEnpoints = { "/user/login", "/user/signup" };

	private final String[] PutPublicEnpoints = { "/user/forgotPassword", "/user/forgotPassword" };

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/static/**").permitAll() // Allow
																											// access to
																											// static
																											// resources
				.requestMatchers(HttpMethod.POST, PostPublicEnpoints).permitAll()
				.requestMatchers(HttpMethod.GET, GetPublicEnpoints).permitAll()
				.requestMatchers(HttpMethod.PUT, PutPublicEnpoints).permitAll().anyRequest().authenticated());

		// Handle 401 Unauthorized responses
		httpSecurity
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(this.jwtTokenUtils.jwtDecoder()))
								.authenticationEntryPoint(new JwtAuthenticationEntryPoint())) // wrong jwt
				// catch 403
				.exceptionHandling().accessDeniedHandler(new JwtAuthorizationEntryPoint()); // if user have not
																							// authorization
		;

		// Disable CSRF protection
		httpSecurity.csrf(csrf -> csrf.disable());

		// // Allowed frontend can access
		httpSecurity.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration corsConfig = new CorsConfiguration();
			corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // we can add
																								// more client
																								// domains
			corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			corsConfig.setAllowedHeaders(Collections.singletonList("*"));
			corsConfig.setAllowCredentials(true);
			return corsConfig;
		}));

		return httpSecurity.build();
	}

}