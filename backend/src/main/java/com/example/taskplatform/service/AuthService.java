package com.example.taskplatform.service;

import com.example.taskplatform.dto.LoginRequest;
import com.example.taskplatform.dto.LoginResponse;
import com.example.taskplatform.model.User;
import com.example.taskplatform.repository.UserRepository;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final Map<String, User> sessions = new ConcurrentHashMap<>();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .filter(u -> u.getPassword().equals(request.password()))
                .orElseThrow(() -> new IllegalArgumentException("用户名或密码错误"));

        String token = UUID.randomUUID().toString();
        sessions.put(token, user);

        return new LoginResponse(token, user.getId(), user.getUsername(), user.getRole());
    }

    public Optional<User> getByToken(String token) {
        return Optional.ofNullable(sessions.get(token));
    }
}
