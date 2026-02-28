package com.example.taskplatform.service;

import com.example.taskplatform.dto.CreateUserRequest;
import com.example.taskplatform.model.Role;
import com.example.taskplatform.model.User;
import com.example.taskplatform.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(CreateUserRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(u -> {
            throw new IllegalArgumentException("用户名已存在");
        });

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setRole(request.role());
        return userRepository.save(user);
    }

    public List<User> findAllNormalUsers() {
        return userRepository.findAll().stream().filter(u -> u.getRole() == Role.USER).toList();
    }
}
