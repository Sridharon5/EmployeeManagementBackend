package com.my.dao;

import com.my.entities.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentDao {
    Department save(Department department);
    List<Department> findAll();
    Optional<Department> findById(Integer id);
    void deleteById(Integer id);
	Department update(Department department);
}
