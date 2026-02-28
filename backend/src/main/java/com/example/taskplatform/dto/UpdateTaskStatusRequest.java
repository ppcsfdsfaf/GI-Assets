package com.example.taskplatform.dto;

import com.example.taskplatform.model.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(@NotNull TaskStatus status) {
}
