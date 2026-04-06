package com.my.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.dto.TaskCreateDto;
import com.my.dto.TaskPatchDto;
import com.my.dto.TaskResponseDto;
import com.my.dto.TaskUpdateDto;
import com.my.entities.Task;
import com.my.services.TaskService;
import com.my.util.SecurityUtil;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping("/add")
	public ResponseEntity<Task> createTask(@RequestBody TaskCreateDto dto) {
		return ResponseEntity.ok(taskService.createTask(SecurityUtil.requireCurrentUser(), dto));
	}

	@GetMapping
	public ResponseEntity<Page<TaskResponseDto>> listTasks(@RequestParam(defaultValue = "my") String scope,
			@RequestParam(required = false) Task.Priority priority,
			@PageableDefault(size = 20, sort = "dueDate", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity
				.ok(taskService.listTasks(SecurityUtil.requireCurrentUser(), scope, priority, pageable));
	}

	@GetMapping("/getAllTasks")
	public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
		return ResponseEntity.ok(taskService.getAllTasksForAdmin(SecurityUtil.requireCurrentUser()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
		return ResponseEntity.ok(taskService.getTaskById(SecurityUtil.requireCurrentUser(), id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<TaskResponseDto> patchTask(@PathVariable Long id, @RequestBody TaskPatchDto body) {
		return ResponseEntity.ok(taskService.patchTask(SecurityUtil.requireCurrentUser(), id, body));
	}

	@PostMapping("/edit/{id}")
	public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDto task) {
		return ResponseEntity.ok(taskService.updateTask(SecurityUtil.requireCurrentUser(), id, task));
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long id) {
		taskService.deleteTask(SecurityUtil.requireCurrentUser(), id);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Task deleted successfully.");
		return ResponseEntity.ok(response);
	}
}
