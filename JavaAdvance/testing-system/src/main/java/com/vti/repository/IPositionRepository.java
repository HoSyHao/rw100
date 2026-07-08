package com.vti.repository;

import com.vti.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.enums.PositionName;

public interface IPositionRepository extends JpaRepository<Position, Integer> {
    boolean existsByName(PositionName name);
}
