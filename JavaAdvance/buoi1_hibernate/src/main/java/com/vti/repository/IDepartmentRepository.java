package com.vti.repository;

import com.vti.entity.Department;

import java.util.List;

public interface IDepartmentRepository {
    List<Department> findAll();
    Department findById(Integer id);
    void create(Department department);
    void update(Department department);
    void delete(Integer id);
}