package com.vti.backend.repository;

import com.vti.entity.Position;

import java.util.List;

public interface IPositionRepository {
    List<Position> getAllPositions();
    List<Position> findByPositionName(String name);
    boolean createPosition(String name);
    boolean updatePosition(String name, int id);
    boolean deletePosition(int id);
}
