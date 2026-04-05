package com.my.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.my.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	boolean existsByUser_Id(Long userId);

	Optional<Employee> findByUser_Id(Long userId);

	@Query("SELECT COUNT(DISTINCT e.id) FROM Employee e")
	long countDistinctById();

}
