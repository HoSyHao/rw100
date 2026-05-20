package com.vti.backend.service.impl;

import com.vti.backend.repository.IPositionRepository;
import com.vti.backend.repository.impl.PositionRepositoryImpl;
import com.vti.backend.service.IPositionService;
import com.vti.entity.Position;

import java.util.List;

public class PositionServiceImpl implements IPositionService {
    private final IPositionRepository positionRepository = new PositionRepositoryImpl();

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.getAllPositions();
    }

    @Override
    public List<Position> findByPositionName(String name) {
        return positionRepository.findByPositionName(name);
    }

    @Override
    public boolean createPosition(String name) {
        return positionRepository.createPosition(name);
    }

    @Override
    public boolean updatePosition(String name, int id) {
        return positionRepository.updatePosition(name, id);
    }

    @Override
    public boolean deletePosition(int id) {
        return positionRepository.deletePosition(id);
    }

    @Override
    public boolean checkExistPosition(String name, Integer id) {
        return positionRepository.checkExistPosition(name, id);
    }
}
