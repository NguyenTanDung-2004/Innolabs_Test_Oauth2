package com.example.Mini_Project1.configuration;

import org.springframework.boot.ApplicationRunner; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Mini_Project1.entity.User;
import com.example.Mini_Project1.repository.UserRepository;
import com.example.Mini_Project1.utils.PasswordUtils;

@Configuration
public class ApplicationInitConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            User user = userRepository.getUserByEmail("admin@gmail.com");
            if (user == null) {
                User user1 = User.builder()
                		.email("admin@gmail.com")
                		.password(PasswordUtils.encryptPassword("PassAdmin123@"))
                		.role("admin")
                		.build();
                userRepository.save(user1);
            }
        };
    }
}
