package com.my.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

import com.my.models.Employee;
import com.my.repository.EmployeeRepository;

@Service
public class serviceImpl implements Services {

    @Autowired
    private EmployeeRepository repo;

    @Override
    public ResponseEntity<Object> create(Employee e) {
        Employee savedEmployee = repo.save(e);
        return ResponseEntity.ok((Object) savedEmployee);  // Casting Employee to Object
    }

    @Override
    public ResponseEntity<Object> update(String id, Employee e) {
        Optional<Employee> existingEmployee = repo.findById(Long.parseLong(id));
        if (existingEmployee.isPresent()) {
            Employee updatedEmployee = existingEmployee.get();
            updatedEmployee.setFirstName(e.getFirstName());
            updatedEmployee.setLastName(e.getLastName());
            updatedEmployee.setEmail(e.getEmail());
            repo.save(updatedEmployee);
            return ResponseEntity.ok((Object) updatedEmployee);  // Casting Employee to Object
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> delete(String id) {
        Optional<Employee> existingEmployee = repo.findById(Long.parseLong(id));
        if (existingEmployee.isPresent()) {
            repo.deleteById(Long.parseLong(id));
            return ResponseEntity.ok((Object) "Employee deleted successfully");  // Casting String to Object
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> get(String id) {
        Optional<Employee> employee = repo.findById(Long.parseLong(id));
        // Using map to return ResponseEntity with Employee or Not Found
        return employee.map(emp -> ResponseEntity.ok((Object) emp))  // Casting Employee to Object
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Object> getAl() {
        List<Employee> employees = repo.findAll();
        return ResponseEntity.ok(employees);  // Cast List to Object
    }


}
