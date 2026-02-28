package com.example.taskplatform.config;

import com.example.taskplatform.model.Role;
import com.example.taskplatform.model.User;
import com.example.taskplatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);

                User userA = new User();
                userA.setUsername("alice");
                userA.setPassword("123456");
                userA.setRole(Role.USER);
                userRepository.save(userA);

                User userB = new User();
                userB.setUsername("bob");
                userB.setPassword("123456");
                userB.setRole(Role.USER);
                userRepository.save(userB);
            }
        };
    }
}
