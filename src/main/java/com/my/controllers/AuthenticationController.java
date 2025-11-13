package com.my.controllers;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.entities.AuthenticationResponse;
import com.my.entities.User;
import com.my.services.AuthenticationService;

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
    
    @GetMapping("/getKeyPointIndicators")
    public ResponseEntity<Object>getKeyPointIndicators(
   
    ){   

    	return ResponseEntity.ok(authService.getKeyPointIndicators());
    }
    
}
