package com.vti.service.impl;

import com.vti.dto.DepartmentDTO;
import com.vti.dto.DepartmentFormForCreate;
import com.vti.dto.DepartmentFormForUpdate;
import com.vti.entity.Department;
import com.vti.exception.DuplicateDataException;
import com.vti.exception.ResourceNotFoundException;
import com.vti.repository.IDepartmentRepository;
import com.vti.service.IDepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll().stream().map(department -> modelMapper.map(department, DepartmentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO findById(Integer id) {
        Department entity = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return modelMapper.map(entity, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO findByName(String name) {
        Department entity = departmentRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with name: " + name));
        return modelMapper.map(entity, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO save(DepartmentFormForCreate form) {
        if (departmentRepository.existsByName(form.getName())) {
            throw new DuplicateDataException("Department name already exists");
        }
        Department entity = new Department();
        entity.setName(form.getName());
        Department savedEntity = departmentRepository.save(entity);
        return modelMapper.map(savedEntity, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO update(Integer id, DepartmentFormForUpdate form) {
        Department entity = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        if (form.getName() != null && !form.getName().equals(entity.getName())) {
            if (departmentRepository.existsByName(form.getName())) {
                throw new DuplicateDataException("Department name already exists");
            }
            entity.setName(form.getName());
        }

        Department updatedEntity = departmentRepository.save(entity);
        return modelMapper.map(updatedEntity, DepartmentDTO.class);
    }

    @Override
    public String delete(Integer id) {
        if(!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
        return "Department with id " + id + " has been deleted successfully";
    }

    @Override
    public String deleteByIds(List<Integer> ids) {
        int count = departmentRepository.customDeleteByIds(ids);
        return "Deleted " + count + " departments successfully";
    }
}
