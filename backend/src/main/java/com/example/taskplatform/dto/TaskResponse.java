package com.example.taskplatform.dto;

import com.example.taskplatform.model.Task;
import com.example.taskplatform.model.TaskStatus;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long createdById,
        String createdByName,
        Long assigneeId,
        String assigneeName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedBy().getId(),
                task.getCreatedBy().getUsername(),
                task.getAssignee() == null ? null : task.getAssignee().getId(),
                task.getAssignee() == null ? null : task.getAssignee().getUsername(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
