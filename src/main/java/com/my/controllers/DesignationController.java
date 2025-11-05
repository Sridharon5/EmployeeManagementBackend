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

import com.my.entities.Designation;
import com.my.services.DesignationService;

@RestController
@RequestMapping("/designations")
@CrossOrigin(origins = "*")
public class DesignationController {

	@Autowired
	private DesignationService DesignationService;

	@PostMapping("/add")
	public ResponseEntity<Designation> createDesignation(@RequestBody Designation designation) {
		return ResponseEntity.ok(DesignationService.createDesignation(designation));
	}

	@GetMapping("/getAllDesignations")
	public ResponseEntity<List<Designation>> getAllDesignations() {
		return ResponseEntity.ok(DesignationService.getAllDesignations());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Designation> getDesignationById(@PathVariable Integer id) {
		return ResponseEntity.ok(DesignationService.getDesignationById(id));
	}

	@PostMapping("/edit/{id}")
	public ResponseEntity<Designation> updateDesignation(@PathVariable Integer id, @RequestBody Designation designation) {
		return ResponseEntity.ok(DesignationService.updateDesignation(id, designation));
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteDesignation(@PathVariable Integer id) {
		DesignationService.deleteDesignation(id);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Designation deleted successfully.");
		return ResponseEntity.ok(response);
	}
}
