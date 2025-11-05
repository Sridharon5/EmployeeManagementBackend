package com.my.services.impl;

import com.my.dao.DesignationDao;
import com.my.entities.Designation;
import com.my.services.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationDao designationDao;

    @Override
    public Designation createDesignation(Designation designation) {
        return designationDao.save(designation);
    }

    @Override
    public List<Designation> getAllDesignations() {
        return designationDao.findAll();
    }

    @Override
    public Designation getDesignationById(Integer id) {
        return designationDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found with ID: " + id));
    }

    @Override
    public Designation updateDesignation(Integer id, Designation designationDetails) {
        Designation designation = getDesignationById(id);
        designation.setTitle(designationDetails.getTitle());
        designation.setDepartment(designationDetails.getDepartment());
        return designationDao.update(designation);
    }

    @Override
    public void deleteDesignation(Integer id) {
        designationDao.deleteById(id);
    }
}
