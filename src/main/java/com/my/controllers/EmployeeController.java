package com.my.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.my.entities.Employee;
import com.my.services.serviceImpl;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final serviceImpl service;

    @Autowired
    public EmployeeController(serviceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Employee e) {
        return service.create(e);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody Employee e) {
        return service.update(id, e);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return service.getAl();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable String id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return service.delete(id);
    }
}
