package com.vti.backend.service;

import com.vti.entity.Position;

import java.util.List;

public interface IPositionService {
    List<Position> getAllPositions();
    List<Position> findByPositionName(String name);
    boolean createPosition(String name);
    boolean updatePosition(String name, int id);
    boolean deletePosition(int id);
}
