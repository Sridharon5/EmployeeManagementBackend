package com.my.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.my.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	@Query("SELECT COUNT(DISTINCT d.id) FROM Department d")
	long countDistinctById();

}
