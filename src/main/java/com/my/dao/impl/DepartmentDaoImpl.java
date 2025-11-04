package com.my.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.my.dao.DepartmentDao;
import com.my.entities.Department;
import com.my.repositories.DepartmentRepository;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

	private final DepartmentRepository departmentRepository;

	public DepartmentDaoImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public Department save(Department department) {
		return departmentRepository.save(department);
	}

	@Override
	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	@Override
	public Optional<Department> findById(Integer id) {
		return departmentRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		departmentRepository.deleteById(id);
	}

	@Override
	public Department update(Department department) {
		Optional<Department> existingDepartment = departmentRepository.findById(department.getId());

		if (existingDepartment.isPresent()) {
			Department depToUpdate = existingDepartment.get();
			depToUpdate.setName(department.getName());
			depToUpdate.setDescription(department.getDescription());
			depToUpdate.setCreatedAt(department.getCreatedAt()); 
			return departmentRepository.save(depToUpdate);
		} else {
			throw new RuntimeException("Department not found with ID: " + department.getId());
		}
	}
}
