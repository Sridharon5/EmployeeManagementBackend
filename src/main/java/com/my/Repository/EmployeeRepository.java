package com.my.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.my.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
