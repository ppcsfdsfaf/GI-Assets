package com.example.taskplatform.repository;

import com.example.taskplatform.model.Task;
import com.example.taskplatform.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignee(User assignee);
}
