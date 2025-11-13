package com.my.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.my.entities.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {
	@Query("SELECT COUNT(DISTINCT d.id) FROM Designation d")
	long countDistinctById();

}
