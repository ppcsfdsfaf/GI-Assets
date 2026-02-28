package com.example.taskplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaskRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull Long assigneeId
) {
}
