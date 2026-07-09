package com.vti.service.impl;

import com.vti.dto.PositionDTO;
import com.vti.dto.PositionFormForCreate;
import com.vti.dto.PositionFormForUpdate;
import com.vti.entity.Position;
import com.vti.exception.DuplicateDataException;
import com.vti.exception.ResourceNotFoundException;
import com.vti.repository.IPositionRepository;
import com.vti.service.IPositionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements IPositionService {
    @Autowired
    private IPositionRepository positionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PositionDTO> findAll() {
        return positionRepository.findAll().stream().map(position -> modelMapper.map(position, PositionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PositionDTO findById(Integer id) {
        Position entity = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
        return modelMapper.map(entity, PositionDTO.class);
    }

    @Override
    public PositionDTO save(PositionFormForCreate form) {
        if (positionRepository.existsByName(form.getName())) {
            throw new DuplicateDataException("Position name already exists");
        }
        Position entity = new Position();
        entity.setName(form.getName());
        Position savedEntity = positionRepository.save(entity);
        return modelMapper.map(savedEntity, PositionDTO.class);
    }

    @Override
    public PositionDTO update(Integer id, PositionFormForUpdate form) {
        Position entity = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));

        if (form.getName() != null && !form.getName().equals(entity.getName())) {
            if (positionRepository.existsByName(form.getName())) {
                throw new DuplicateDataException("Position name already exists");
            }
            entity.setName(form.getName());
        }

        Position updatedEntity = positionRepository.save(entity);
        return modelMapper.map(updatedEntity, PositionDTO.class);
    }

    @Override
    public String delete(Integer id) {
        if(!positionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Position not found with id: " + id);
        }
        positionRepository.deleteById(id);
        return "Position with id " + id + " has been deleted successfully";
    }

    @Override
    public String deleteByIds(List<Integer> ids) {
        int count = positionRepository.customDeleteByIds(ids);
        return "Deleted " + count + " positions successfully";
    }
}
