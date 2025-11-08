package com.my.services;

import com.my.dto.EmployeeUpdateDto;
import com.my.entities.Employee;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Integer id);
    Employee updateEmployee(Integer id, EmployeeUpdateDto employee);
    void deleteEmployee(Integer id);
}
