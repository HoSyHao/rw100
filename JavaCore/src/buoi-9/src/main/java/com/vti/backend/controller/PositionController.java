package com.vti.backend.controller;

import com.vti.backend.service.IPositionService;
import com.vti.backend.service.impl.PositionServiceImpl;
import com.vti.entity.Position;

import java.util.List;

public class PositionController {
    private final IPositionService positionService = new PositionServiceImpl();

    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    public List<Position> findByPositionName(String name) {
        return positionService.findByPositionName(name);
    }

    public boolean createPosition(String name) {
        return positionService.createPosition(name);
    }

    public boolean updatePosition(String name, int id) {
        return positionService.updatePosition(name, id);
    }

    public boolean deletePosition(int id) {
        return positionService.deletePosition(id);
    }
}
