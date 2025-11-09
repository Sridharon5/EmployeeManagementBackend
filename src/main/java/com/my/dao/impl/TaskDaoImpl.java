
package com.my.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.my.dao.TaskDao;
import com.my.dto.TaskUpdateDto;
import com.my.entities.Department;
import com.my.entities.Designation;
import com.my.entities.Task;
import com.my.entities.User;
import com.my.repositories.DepartmentRepository;
import com.my.repositories.DesignationRepository;
import com.my.repositories.TaskRepository;
import com.my.repositories.UserRepository;

@Repository
public class TaskDaoImpl implements TaskDao {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	private final DesignationRepository designationRepository;
	private final DepartmentRepository departmentRepository;

	public TaskDaoImpl(TaskRepository taskRepository,UserRepository userRepository,
			DesignationRepository designationRepository,DepartmentRepository departmentRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
		this.designationRepository = designationRepository;
		this.departmentRepository = departmentRepository;
	}

	@Override
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Optional<Task> findById(Integer id) {
		return taskRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		taskRepository.deleteById(id);
	}
	@Override
	public Optional<Task> update(TaskUpdateDto dto) {
	    Task task = taskRepository.findById(dto.getId())
	            .orElseThrow(() -> new RuntimeException("Task not found with ID: " + dto.getId()));

	    
	    return taskRepository.findById(dto.getId());
	}

}
