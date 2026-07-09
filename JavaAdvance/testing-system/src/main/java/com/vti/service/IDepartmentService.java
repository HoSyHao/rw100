package com.vti.service;

import com.vti.dto.DepartmentDTO;
import com.vti.dto.DepartmentFormForCreate;
import com.vti.dto.DepartmentFormForUpdate;

import java.util.List;

public interface IDepartmentService {
    public List<DepartmentDTO> findAll();
    public DepartmentDTO findById(Integer id);
    public DepartmentDTO findByName(String name);
    public DepartmentDTO save(DepartmentFormForCreate form);
    public DepartmentDTO update(Integer id, DepartmentFormForUpdate form);
    public String delete(Integer id);
    public String deleteByIds(List<Integer> ids);
}
