package com.my.services.impl;

import com.my.dao.TaskDao;
import com.my.dto.TaskUpdateDto;
import com.my.entities.Task;
import com.my.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public Task createTask(Task task) {
        return taskDao.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskDao.findAll();
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
    }

    @Override
    public Optional<Task> updateTask(Integer id, TaskUpdateDto taskDetails) {
       
        return taskDao.update(taskDetails);
    }

    @Override
    public void deleteTask(Integer id) {
        taskDao.deleteById(id);
    }
}
