package com.vti.backend.service.impl;

import com.vti.backend.repository.IDepartmentRepository;
import com.vti.backend.repository.impl.DepartmentRepositoryImpl;
import com.vti.backend.service.IDepartmentService;
import com.vti.backend.service.importcsv.impl.DepartmentImportStrategy;
import com.vti.backend.service.importcsv.impl.ImportCSVServiceImpl;
import com.vti.entity.Department;

import java.util.*;

import com.vti.backend.service.importcsv.IImportCSVService;
import com.vti.dto.context.DepartmentImportContext;
import com.vti.dto.csv.DepartmentCSV;

public class DepartmentServiceImpl implements IDepartmentService {
    private final IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    @Override
    public List<Department> findDepartmentByIdAndName(String searchName, int searchId) {
        return departmentRepository.findDepartmentByIdAndName(searchName, searchId);
    }

    @Override
    public List<Department> findeDepartmentHaveMoreThan2Acc() {
        return departmentRepository.findeDepartmentHaveMoreThan2Acc();
    }

    @Override
    public boolean createDepartment(String departmentName) {
        return departmentRepository.createDepartment(departmentName);
    }

    @Override
    public boolean deleteDepartment(int id) {
        return departmentRepository.deleteDepartment(id);
    }

    @Override
    public boolean updateDepartment(int id, String departmentName) {
        return departmentRepository.updateDepartment(id, departmentName);
    }

    @Override
    public boolean checkExistDepartment(String departmentName, Integer departmentId) {
        return departmentRepository.checkExistDepartment(departmentName, departmentId);
    }

    @Override
    public String importDepartmentCSV(String pathName) {
        IImportCSVService<DepartmentImportContext, Department, DepartmentCSV> importCSVService = new ImportCSVServiceImpl<>();
        return importCSVService.importCSV(pathName, new DepartmentImportStrategy(departmentRepository));
    }

}
