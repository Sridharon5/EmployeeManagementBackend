package com.my.dao;

import com.my.entities.Designation;
import java.util.List;
import java.util.Optional;

public interface DesignationDao {
    Designation save(Designation designation);
    List<Designation> findAll();
    Optional<Designation> findById(Integer id);
    void deleteById(Integer id);
	Designation update(Designation designation);
}
