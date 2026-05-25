package com.vti.backend.repository;

import com.vti.entity.Department;

import java.util.List;

public interface IDepartmentRepository {
    List<Department> getAllDepartments();
    List<Department> findDepartmentByIdAndName(String searchName, int searchId);
    List<Department> findeDepartmentHaveMoreThan2Acc();
    boolean createDepartment(String departmentName);
    boolean deleteDepartment(int id);
    boolean updateDepartment(int id, String departmentName);
    boolean checkExistDepartment(String departmentName, Integer departmentId);
    boolean createDepartments(List<Department> departments);
    List<String> findExistingNames(List<String> names);
    List<Integer> findExistingIds(List<Integer> ids);
}
