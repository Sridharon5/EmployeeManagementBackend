package com.my.services;

import com.my.entities.Department;
import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);
    List<Department> getAllDepartments();
    Department getDepartmentById(Integer id);
    Department updateDepartment(Integer id, Department department);
    void deleteDepartment(Integer id);
}
