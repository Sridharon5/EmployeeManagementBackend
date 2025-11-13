package com.my.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.my.entities.AuthenticationResponse;
import com.my.entities.User;
import com.my.repositories.DepartmentRepository;
import com.my.repositories.DesignationRepository;
import com.my.repositories.EmployeeRepository;
import com.my.repositories.TaskRepository;
import com.my.repositories.UserRepository;

@Service
public class AuthenticationService {
	private final UserRepository repository;
	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TaskRepository taskRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository repository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public String register(User request) {
		if (repository.existsByUsername(request.getUsername())) {
			return "User already exists.Please choose another username.";
		}
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		repository.save(user);
		return "User registered successfully";
	}

	public AuthenticationResponse login(User request) {
		System.out.println("Authenticating user...");

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		User user = repository.findByUsername(request.getUsername())

				.orElseThrow(() -> new RuntimeException("User not found"));
		System.out.print(user.getRole());

		String token = jwtService.generateToken(user);
		System.out.println(token);

		return new AuthenticationResponse(token, user.getRole(), user.getFirstName());
	}

	 public Object getKeyPointIndicators() {
	        List<Map<String, Object>> stats = new ArrayList<>();
	       long deptCount = departmentRepository.countDistinctById();
	       long desigCount = designationRepository.countDistinctById();
	       long userCount = repository.countDistinctById();
	       long empCount = employeeRepository.countDistinctById();
	       long evalCount = 0;
	       long taskCount = taskRepository.countDistinctById();

	        stats.add(Map.of("count", deptCount, "label", "Total Departments", "icon", "bi-clipboard", "color", "blue"));
	        stats.add(Map.of("count", desigCount, "label", "Total Designations", "icon", "bi-diagram-3", "color", "green"));
	        stats.add(Map.of("count", userCount, "label", "Total Users", "icon", "bi-person", "color", "yellow"));
	        stats.add(Map.of("count", empCount, "label", "Total Employees", "icon", "bi-people", "color", "purple"));
	        stats.add(Map.of("count", evalCount, "label", "Total Evaluators", "icon", "bi-eye", "color", "violet"));
	        stats.add(Map.of("count", taskCount, "label", "Total Tasks", "icon", "bi-alarm", "color", "cyan"));

	        return stats;
	    }

}
