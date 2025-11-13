package com.my.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
	@Query("SELECT COUNT(DISTINCT t.id) FROM Task t")
	long countDistinctById();

}
