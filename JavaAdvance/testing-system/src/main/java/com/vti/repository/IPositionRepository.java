package com.vti.repository;

import com.vti.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.vti.enums.PositionName;

public interface IPositionRepository extends JpaRepository<Position, Integer> {
    boolean existsByName(PositionName name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Position p WHERE p.id IN :ids")
    int customDeleteByIds(List<Integer> ids);
}
