
package com.my.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.my.dao.DesignationDao;
import com.my.entities.Designation;
import com.my.repositories.DesignationRepository;

@Repository
public class DesignationDaoImpl implements DesignationDao {

	private final DesignationRepository designationRepository;

	public DesignationDaoImpl(DesignationRepository designationRepository) {
		this.designationRepository = designationRepository;
	}

	@Override
	public Designation save(Designation designation) {
		return designationRepository.save(designation);
	}

	@Override
	public List<Designation> findAll() {
		return designationRepository.findAll();
	}

	@Override
	public Optional<Designation> findById(Integer id) {
		return designationRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		designationRepository.deleteById(id);
	}

	@Override
	public Designation update(Designation designation) {
		Optional<Designation> existingDesignation = designationRepository.findById(designation.getId());

		if (existingDesignation.isPresent()) {
			Designation depToUpdate = existingDesignation.get();
			depToUpdate.setTitle(designation.getTitle());
			depToUpdate.setDepartment(designation.getDepartment());
			depToUpdate.setCreatedAt(designation.getCreatedAt()); 
			return designationRepository.save(depToUpdate);
		} else {
			throw new RuntimeException("Designation not found with ID: " + designation.getId());
		}
	}
}
