package com.vti.service;

import com.vti.dto.PositionDTO;
import com.vti.dto.PositionFormForCreate;
import com.vti.dto.PositionFormForUpdate;

import java.util.List;

public interface IPositionService {
    public List<PositionDTO> findAll();
    public PositionDTO findById(Integer id);
    public PositionDTO save(PositionFormForCreate form);
    public PositionDTO update(Integer id, PositionFormForUpdate form);
    public String delete(Integer id);
    public String deleteByIds(List<Integer> ids);
}
