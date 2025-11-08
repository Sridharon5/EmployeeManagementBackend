package com.my.services.impl;

import com.my.dao.EmployeeDao;
import com.my.dto.EmployeeUpdateDto;
import com.my.entities.Employee;
import com.my.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    @Override
    public Employee updateEmployee(Integer id, EmployeeUpdateDto employeeDetails) {
       
        return employeeDao.update(employeeDetails);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeDao.deleteById(id);
    }
}
