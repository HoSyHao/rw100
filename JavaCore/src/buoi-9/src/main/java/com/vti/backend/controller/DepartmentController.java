package com.vti.backend.controller;

import com.vti.backend.service.IDepartmentService;
import com.vti.backend.service.impl.DepartmentServiceImpl;
import com.vti.entity.Department;

import java.util.List;

public class DepartmentController {
    private final IDepartmentService departmentService = new DepartmentServiceImpl();

    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    public List<Department> findDepartmentByIdAndName(String searchName, int searchId) {
        return departmentService.findDepartmentByIdAndName(searchName, searchId);
    }

    public List<Department> findDepartmentHaveMoreThan2Acc() {
        return departmentService.findeDepartmentHaveMoreThan2Acc();
    }

    public boolean createDepartment(String departmentName) {
        return departmentService.createDepartment(departmentName);
    }

    public boolean deleteDepartment(int id) {
        return departmentService.deleteDepartment(id);
    }

    public boolean updateDepartment(int id, String departmentName) {
        return departmentService.updateDepartment(id, departmentName);
    }
}
