package com.example.taskplatform.dto;

import com.example.taskplatform.model.Role;

public record LoginResponse(
        String token,
        Long userId,
        String username,
        Role role
) {
}
