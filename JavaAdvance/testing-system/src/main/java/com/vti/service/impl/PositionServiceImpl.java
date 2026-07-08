package com.vti.service.impl;

import com.vti.dto.PositionDTO;
import com.vti.dto.PositionFormForCreate;
import com.vti.dto.PositionFormForUpdate;
import com.vti.entity.Position;
import com.vti.exception.ResourceNotFoundException;
import com.vti.repository.IPositionRepository;
import com.vti.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionServiceImpl implements IPositionService {
    @Autowired
    private IPositionRepository positionRepository;

    @Override
    public List<PositionDTO> findAll() {
        List<Position> positions = positionRepository.findAll();
        List<PositionDTO> dtos = new ArrayList<>();
        for (Position entity : positions) {
            dtos.add(mapToDTO(entity));
        }
        return dtos;
    }

    @Override
    public PositionDTO findById(Integer id) {
        Position entity = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
        return mapToDTO(entity);
    }

    @Override
    public PositionDTO save(PositionFormForCreate form) {
        Position entity = new Position();
        entity.setName(form.getName());
        Position savedEntity = positionRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public PositionDTO update(Integer id, PositionFormForUpdate form) {
        Position entity = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + id));
        entity.setName(form.getName());
        Position updatedEntity = positionRepository.save(entity);
        return mapToDTO(updatedEntity);
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
        positionRepository.deleteAllByIdInBatch(ids);
        return "Deleted " + ids.size() + " positions successfully";
    }

    private PositionDTO mapToDTO(Position entity) {
        PositionDTO dto = new PositionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
