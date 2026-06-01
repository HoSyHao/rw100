package com.vti.dto.csv;

public class DepartmentCSV {
    private String departmentName;

    public DepartmentCSV() {}

    public DepartmentCSV(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
