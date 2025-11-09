package com.my.dao;

import com.my.dto.TaskUpdateDto;
import com.my.entities.Task;
import java.util.List;
import java.util.Optional;

public interface TaskDao {
    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(Integer id);
    void deleteById(Integer id);
	Optional<Task> update(TaskUpdateDto task);
}
