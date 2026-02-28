package com.example.taskplatform.service;

import com.example.taskplatform.dto.CreateTaskRequest;
import com.example.taskplatform.model.Role;
import com.example.taskplatform.model.Task;
import com.example.taskplatform.model.TaskStatus;
import com.example.taskplatform.model.User;
import com.example.taskplatform.repository.TaskRepository;
import com.example.taskplatform.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(CreateTaskRequest request, User creator) {
        if (creator.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("仅管理员可以发放任务");
        }

        User assignee = userRepository.findById(request.assigneeId())
                .orElseThrow(() -> new IllegalArgumentException("被分配用户不存在"));

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(TaskStatus.NEW);
        task.setCreatedBy(creator);
        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    public List<Task> getVisibleTasks(User user) {
        if (user.getRole() == Role.ADMIN) {
            return taskRepository.findAll();
        }
        return taskRepository.findByAssignee(user);
    }

    public Task updateStatus(Long taskId, TaskStatus status, User operator) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("任务不存在"));

        if (operator.getRole() != Role.ADMIN
                && (task.getAssignee() == null || !task.getAssignee().getId().equals(operator.getId()))) {
            throw new IllegalArgumentException("无权限修改该任务");
        }

        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task transfer(Long taskId, Long assigneeId, User operator) {
        if (operator.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("仅管理员可以流转任务");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("任务不存在"));
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new IllegalArgumentException("目标用户不存在"));
        task.setAssignee(assignee);
        task.setStatus(TaskStatus.IN_PROGRESS);
        return taskRepository.save(task);
    }
}
