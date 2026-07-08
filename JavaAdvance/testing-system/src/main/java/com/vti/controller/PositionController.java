package com.vti.controller;

import com.vti.dto.PositionDTO;
import com.vti.dto.PositionFormForCreate;
import com.vti.dto.PositionFormForUpdate;
import com.vti.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/positions")
@Validated
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @GetMapping
    public ResponseEntity<List<PositionDTO>> findAll(){
        return new ResponseEntity<>(positionService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDTO> findById(@PathVariable Integer id){
        return new ResponseEntity<>(positionService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PositionDTO> save(@Valid @RequestBody PositionFormForCreate form){
        return new ResponseEntity<>(positionService.save(form), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionDTO> update(@PathVariable Integer id, @Valid @RequestBody PositionFormForUpdate form){
        return new ResponseEntity<>(positionService.update(id, form), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return new ResponseEntity<>(positionService.delete(id), HttpStatus.OK);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<String> deleteByIds(@RequestBody List<Integer> ids){
        return new ResponseEntity<>(positionService.deleteByIds(ids), HttpStatus.OK);
    }
}
