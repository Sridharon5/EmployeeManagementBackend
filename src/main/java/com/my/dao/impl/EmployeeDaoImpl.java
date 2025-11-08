
package com.my.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.my.dao.EmployeeDao;
import com.my.dto.EmployeeUpdateDto;
import com.my.entities.Department;
import com.my.entities.Designation;
import com.my.entities.Employee;
import com.my.entities.User;
import com.my.repositories.DepartmentRepository;
import com.my.repositories.DesignationRepository;
import com.my.repositories.EmployeeRepository;
import com.my.repositories.UserRepository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private final EmployeeRepository employeeRepository;
	private final UserRepository userRepository;
	private final DesignationRepository designationRepository;
	private final DepartmentRepository departmentRepository;

	public EmployeeDaoImpl(EmployeeRepository employeeRepository,UserRepository userRepository,
			DesignationRepository designationRepository,DepartmentRepository departmentRepository) {
		this.employeeRepository = employeeRepository;
		this.userRepository = userRepository;
		this.designationRepository = designationRepository;
		this.departmentRepository = departmentRepository;
	}

	@Override
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(Integer id) {
		return employeeRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		employeeRepository.deleteById(id);
	}
	@Override
	public Employee update(EmployeeUpdateDto dto) {
	    Employee employee = employeeRepository.findById(dto.getEmployeeId())
	            .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId()));

	    if (employee.getUser() != null) {
	        User user = employee.getUser();
	        user.setFirstName(dto.getFirstName());
	        user.setLastName(dto.getLastName());
	        userRepository.save(user);
	    } else {
	        throw new RuntimeException("No user linked to employee ID: " + dto.getEmployeeId());
	    }

	    if (dto.getDesignationId() != null) {
	        Designation designation = designationRepository.findById(dto.getDesignationId())
	                .orElseThrow(() -> new RuntimeException("Designation not found with ID: " + dto.getDesignationId()));
	        designation.setTitle(dto.getDesignationTitle());
	        designationRepository.save(designation);
	    }

	    if (dto.getDepartmentId() != null) {
	        Department department = departmentRepository.findById(dto.getDepartmentId())
	                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));
	        department.setName(dto.getDepartmentName());
	        departmentRepository.save(department);
	    }
	    System.out.println("I have excecuted these method");

	    return employeeRepository.findById(dto.getEmployeeId()).get();
	}

}
