package com.my.controllers;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.entities.AuthenticationResponse;
import com.my.entities.Role;
import com.my.entities.User;
import com.my.services.AuthenticationService;
import com.my.util.SecurityUtil;

@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {
	@Autowired
    private final AuthenticationService authService;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	public AuthenticationController(AuthenticationService authService) {
		this.authService = authService;
	}
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@RequestBody User request) {
	    String result = authService.register(request);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", result);

	    return ResponseEntity.ok(response);
	}

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(
    @RequestBody User request
    ){   

    	return ResponseEntity.ok(authService.login(request));
    }

	@PostMapping("/refresh")
	public ResponseEntity<AuthenticationResponse> refresh() {
		User user = SecurityUtil.requireCurrentUser();
		return ResponseEntity.ok(authService.refresh(user));
	}
    
    @GetMapping("/getKeyPointIndicators")
    public ResponseEntity<Object> getKeyPointIndicators() {
    	User user = SecurityUtil.requireCurrentUser();
    	if (user.getRole() != Role.ADMIN && user.getRole() != Role.MANAGER) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    	}
    	return ResponseEntity.ok(authService.getKeyPointIndicators());
    }
    
}
