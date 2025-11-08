package com.my.dao;

import com.my.dto.EmployeeUpdateDto;
import com.my.entities.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Employee save(Employee employee);
    List<Employee> findAll();
    Optional<Employee> findById(Integer id);
    void deleteById(Integer id);
	Employee update(EmployeeUpdateDto employee);
}
