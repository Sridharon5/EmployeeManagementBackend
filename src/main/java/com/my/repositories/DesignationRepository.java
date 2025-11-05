package com.my.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.my.entities.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {
	
}
