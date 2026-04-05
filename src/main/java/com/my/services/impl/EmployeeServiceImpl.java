package com.my.services.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.my.dao.EmployeeDao;
import com.my.dto.EmployeeCreateDto;
import com.my.dto.EmployeeUpdateDto;
import com.my.dto.UserOptionDto;
import com.my.entities.Department;
import com.my.entities.Designation;
import com.my.entities.Employee;
import com.my.entities.Role;
import com.my.entities.User;
import com.my.repositories.DepartmentRepository;
import com.my.repositories.DesignationRepository;
import com.my.repositories.EmployeeRepository;
import com.my.repositories.UserRepository;
import com.my.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final String DEFAULT_NEW_USER_PASSWORD = "admin";

	private final EmployeeDao employeeDao;
	private final UserRepository userRepository;
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final DesignationRepository designationRepository;
	private final PasswordEncoder passwordEncoder;

	public EmployeeServiceImpl(EmployeeDao employeeDao, UserRepository userRepository,
			EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
			DesignationRepository designationRepository, PasswordEncoder passwordEncoder) {
		this.employeeDao = employeeDao;
		this.userRepository = userRepository;
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.designationRepository = designationRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public Employee createEmployee(EmployeeCreateDto dto) {
		if (dto.getDepartmentId() == null || dto.getDesignationId() == null || dto.getHireDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"departmentId, designationId, and hireDate are required");
		}
		Department dept = departmentRepository.findById(dto.getDepartmentId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department not found"));
		Designation desig = designationRepository.findById(dto.getDesignationId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Designation not found"));

		User user = resolveUserForNewEmployee(dto);

		Employee emp = new Employee();
		emp.setUser(user);
		emp.setDepartment(dept);
		emp.setDesignation(desig);
		emp.setHireDate(dto.getHireDate());
		emp.setStatus(Employee.Status.ACTIVE);
		return employeeDao.save(emp);
	}

	private User resolveUserForNewEmployee(EmployeeCreateDto dto) {
		if (dto.getUserId() != null) {
			User user = userRepository.findById(dto.getUserId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
			if (employeeRepository.existsByUser_Id(user.getId())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already has an employee record");
			}
			return user;
		}

		String username = dto.getUsername() == null ? "" : dto.getUsername().trim();
		if (username.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Either userId (existing account) or username (new or existing without employee) is required");
		}
		String fn = dto.getFirstName() != null ? dto.getFirstName().trim() : "";
		String ln = dto.getLastName() != null ? dto.getLastName().trim() : "";
		if (fn.isEmpty() || ln.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "firstName and lastName are required");
		}

		if (userRepository.existsByUsername(username)) {
			User existing = userRepository.findByUsername(username)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
			if (employeeRepository.existsByUser_Id(existing.getId())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"User '" + username + "' already has an employee record");
			}
			existing.setFirstName(fn);
			existing.setLastName(ln);
			return userRepository.save(existing);
		}

		User created = new User();
		created.setUsername(username);
		created.setFirstName(fn);
		created.setLastName(ln);
		String plain = (dto.getPassword() != null && !dto.getPassword().isBlank()) ? dto.getPassword().trim()
				: DEFAULT_NEW_USER_PASSWORD;
		created.setPassword(passwordEncoder.encode(plain));
		created.setRole(dto.getRole() != null ? dto.getRole() : Role.USER);
		return userRepository.save(created);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserOptionDto> listUsersWithoutEmployee() {
		return userRepository.findUsersWithoutEmployee().stream()
				.map(u -> new UserOptionDto(u.getId(), u.getUsername(), u.getFirstName(), u.getLastName())).toList();
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		return employeeDao.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
	}

	@Override
	public Employee updateEmployee(Long id, EmployeeUpdateDto employeeDetails) {
		return employeeDao.update(employeeDetails);
	}

	@Override
	public void deleteEmployee(Long id) {
		employeeDao.deleteById(id);
	}
}
