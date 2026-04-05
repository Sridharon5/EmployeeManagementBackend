package com.my.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.dto.UserDashboardDto;
import com.my.services.TaskService;
import com.my.util.SecurityUtil;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

	@Autowired
	private TaskService taskService;

	@GetMapping("/me")
	public ResponseEntity<UserDashboardDto> myDashboard() {
		return ResponseEntity.ok(taskService.getMyDashboard(SecurityUtil.requireCurrentUser()));
	}
}
