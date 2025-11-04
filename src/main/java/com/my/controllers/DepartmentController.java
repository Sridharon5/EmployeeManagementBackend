package com.my.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.entities.Department;
import com.my.services.DepartmentService;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping("/add")
	public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
		return ResponseEntity.ok(departmentService.createDepartment(department));
	}

	@GetMapping("/getAllDepartments")
	public ResponseEntity<List<Department>> getAllDepartments() {
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable Integer id) {
		return ResponseEntity.ok(departmentService.getDepartmentById(id));
	}

	@PostMapping("/edit/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
		return ResponseEntity.ok(departmentService.updateDepartment(id, department));
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteDepartment(@PathVariable Integer id) {
		departmentService.deleteDepartment(id);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Department deleted successfully.");
		return ResponseEntity.ok(response);
	}
}
