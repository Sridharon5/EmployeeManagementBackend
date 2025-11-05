package com.my.services;

import com.my.entities.Designation;
import java.util.List;

public interface DesignationService {
    Designation createDesignation(Designation designation);
    List<Designation> getAllDesignations();
    Designation getDesignationById(Integer id);
    Designation updateDesignation(Integer id, Designation designation);
    void deleteDesignation(Integer id);
}
