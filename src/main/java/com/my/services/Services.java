package com.my.services;

import org.springframework.http.ResponseEntity;

import com.my.entities.Employee;

public interface Services {
     public abstract ResponseEntity<Object> create(Employee e);
	 public abstract ResponseEntity<Object> update(String id,Employee e);
	 public abstract ResponseEntity<Object> delete(String id);
	 public abstract ResponseEntity<Object> get(String id);
	 public abstract ResponseEntity<Object> getAl();
}
