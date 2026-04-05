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

import com.my.dto.EmployeeCreateDto;
import com.my.dto.EmployeeUpdateDto;
import com.my.dto.UserOptionDto;
import com.my.entities.Employee;
import com.my.services.EmployeeService;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

	@Autowired
	private EmployeeService EmployeeService;

	@PostMapping("/add")
	public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeCreateDto dto) {
		return ResponseEntity.ok(EmployeeService.createEmployee(dto));
	}

	@GetMapping("/unlinked-users")
	public ResponseEntity<List<UserOptionDto>> listUnlinkedUsers() {
		return ResponseEntity.ok(EmployeeService.listUsersWithoutEmployee());
	}

	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(EmployeeService.getAllEmployees());
	}

	/** Only digits so paths like {@code /employees/unlinked-users} are not captured as an id. */
	@GetMapping("/{id:\\d+}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		return ResponseEntity.ok(EmployeeService.getEmployeeById(id));
	}

	@PostMapping("/edit/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeUpdateDto employee) {
		return ResponseEntity.ok(EmployeeService.updateEmployee(id, employee));
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Long id) {
		EmployeeService.deleteEmployee(id);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Employee deleted successfully.");
		return ResponseEntity.ok(response);
	}
}
