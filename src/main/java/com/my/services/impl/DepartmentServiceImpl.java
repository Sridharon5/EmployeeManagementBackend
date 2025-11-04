package com.my.services.impl;

import com.my.dao.DepartmentDao;
import com.my.entities.Department;
import com.my.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public Department createDepartment(Department department) {
        return departmentDao.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentDao.findAll();
    }

    @Override
    public Department getDepartmentById(Integer id) {
        return departmentDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    @Override
    public Department updateDepartment(Integer id, Department departmentDetails) {
        Department department = getDepartmentById(id);
        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        return departmentDao.update(department);
    }

    @Override
    public void deleteDepartment(Integer id) {
        departmentDao.deleteById(id);
    }
}
