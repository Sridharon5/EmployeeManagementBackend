package com.my.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.my.models.AuthenticationResponse;
import com.my.models.User;
import com.my.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Logger setup


@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {
	@Autowired
    private final AuthenticationService authService;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	public AuthenticationController(AuthenticationService authService) {
		this.authService = authService;
	}
    @PostMapping("/register")
    public ResponseEntity<String>register(
    @RequestBody User request
    ){
    	return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(
    @RequestBody User request
    ){   System.out.println("Error in me Controller");
    	return ResponseEntity.ok(authService.login(request));
    }
    
}
