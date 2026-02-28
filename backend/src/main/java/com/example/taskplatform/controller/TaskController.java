package com.example.taskplatform.controller;

import com.example.taskplatform.config.UserContext;
import com.example.taskplatform.dto.CreateTaskRequest;
import com.example.taskplatform.dto.TaskResponse;
import com.example.taskplatform.dto.UpdateTaskStatusRequest;
import com.example.taskplatform.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> listTasks() {
        return taskService.getVisibleTasks(UserContext.get()).stream().map(TaskResponse::from).toList();
    }

    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        return TaskResponse.from(taskService.createTask(request, UserContext.get()));
    }

    @PatchMapping("/{taskId}/status")
    public TaskResponse updateStatus(@PathVariable Long taskId, @Valid @RequestBody UpdateTaskStatusRequest request) {
        return TaskResponse.from(taskService.updateStatus(taskId, request.status(), UserContext.get()));
    }

    @PatchMapping("/{taskId}/transfer")
    public TaskResponse transfer(@PathVariable Long taskId, @RequestBody Map<String, Long> request) {
        Long assigneeId = request.get("assigneeId");
        return TaskResponse.from(taskService.transfer(taskId, assigneeId, UserContext.get()));
    }
}
