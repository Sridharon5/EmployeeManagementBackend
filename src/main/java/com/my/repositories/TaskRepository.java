package com.my.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.entities.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {

}
