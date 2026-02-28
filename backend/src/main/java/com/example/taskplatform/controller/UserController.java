package com.example.taskplatform.controller;

import com.example.taskplatform.config.UserContext;
import com.example.taskplatform.dto.CreateUserRequest;
import com.example.taskplatform.model.Role;
import com.example.taskplatform.model.User;
import com.example.taskplatform.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Map<String, Object>> listUsers() {
        return userService.findAll().stream().map(this::toMap).toList();
    }

    @GetMapping("/assignees")
    public List<Map<String, Object>> listAssignees() {
        return userService.findAllNormalUsers().stream().map(this::toMap).toList();
    }

    @PostMapping
    public Map<String, Object> createUser(@Valid @RequestBody CreateUserRequest request) {
        if (UserContext.get().getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("仅管理员可创建用户");
        }
        return toMap(userService.create(request));
    }

    private Map<String, Object> toMap(User user) {
        return Map.of("id", user.getId(), "username", user.getUsername(), "role", user.getRole());
    }
}
