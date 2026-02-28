package com.example.taskplatform.dto;

import com.example.taskplatform.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotNull Role role
) {
}
