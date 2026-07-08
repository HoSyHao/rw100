package com.vti.service.impl;

import com.vti.dto.DepartmentDTO;
import com.vti.dto.DepartmentFormForCreate;
import com.vti.dto.DepartmentFormForUpdate;
import com.vti.entity.Department;
import com.vti.exception.DuplicateDataException;
import com.vti.exception.ResourceNotFoundException;
import com.vti.repository.IDepartmentRepository;
import com.vti.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO findById(Integer id) {
        Department entity = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return mapToDTO(entity);
    }

    @Override
    public DepartmentDTO save(DepartmentFormForCreate form) {
        if (departmentRepository.existsByName(form.getName())) {
            throw new DuplicateDataException("Department name already exists");
        }
        Department entity = new Department();
        entity.setName(form.getName());
        Department savedEntity = departmentRepository.save(entity);
        return mapToDTO(savedEntity);
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
        return mapToDTO(updatedEntity);
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
        departmentRepository.deleteAllByIdInBatch(ids);
        return "Deleted " + ids.size() + " departments successfully";
    }
    
    private DepartmentDTO mapToDTO(Department entity) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
