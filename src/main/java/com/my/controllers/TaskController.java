package com.my.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.dto.TaskUpdateDto;
import com.my.entities.Task;
import com.my.services.TaskService;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping("/add")
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		return ResponseEntity.ok(taskService.createTask(task));
	}

	@GetMapping("/getAllTasks")
	public ResponseEntity<List<Task>> getAllTasks() {
		return ResponseEntity.ok(taskService.getAllTasks());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
		return ResponseEntity.ok(taskService.getTaskById(id));
	}

	@PostMapping("/edit/{id}")
	public ResponseEntity<Optional<Task>> updateTask(@PathVariable Integer id, @RequestBody TaskUpdateDto task) {
		return ResponseEntity.ok(taskService.updateTask(id, task));
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Integer id) {
		taskService.deleteTask(id);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Task deleted successfully.");
		return ResponseEntity.ok(response);
	}
}
