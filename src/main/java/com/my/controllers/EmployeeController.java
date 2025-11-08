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

import com.my.dto.EmployeeUpdateDto;
import com.my.entities.Employee;
import com.my.services.EmployeeService;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

	@Autowired
	private EmployeeService EmployeeService;

	@PostMapping("/add")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		return ResponseEntity.ok(EmployeeService.createEmployee(employee));
	}

	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(EmployeeService.getAllEmployees());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
		return ResponseEntity.ok(EmployeeService.getEmployeeById(id));
	}

	@PostMapping("/edit/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeUpdateDto employee) {
		return ResponseEntity.ok(EmployeeService.updateEmployee(id, employee));
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Integer id) {
		EmployeeService.deleteEmployee(id);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Employee deleted successfully.");
		return ResponseEntity.ok(response);
	}
}
