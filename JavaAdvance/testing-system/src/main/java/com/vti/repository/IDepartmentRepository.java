package com.vti.repository;

import com.vti.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository extends JpaRepository<Department,Integer> {
    boolean existsByName(String name);
    Optional<Department> findByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Department d WHERE d.id IN :ids")
    int customDeleteByIds(List<Integer> ids);
}
