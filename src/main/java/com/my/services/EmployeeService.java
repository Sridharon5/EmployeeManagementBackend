package com.my.services;

import java.util.List;

import com.my.dto.EmployeeCreateDto;
import com.my.dto.EmployeeUpdateDto;
import com.my.dto.UserOptionDto;
import com.my.entities.Employee;

public interface EmployeeService {
    Employee createEmployee(EmployeeCreateDto dto);

    List<UserOptionDto> listUsersWithoutEmployee();

    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, EmployeeUpdateDto employee);
    void deleteEmployee(Long id);
}
