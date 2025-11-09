package com.my.services;

import com.my.dto.TaskUpdateDto;
import com.my.entities.Task;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    List<Task> getAllTasks();
    Task getTaskById(Integer id);
    Optional<Task> updateTask(Integer id, TaskUpdateDto task);
    void deleteTask(Integer id);
}
