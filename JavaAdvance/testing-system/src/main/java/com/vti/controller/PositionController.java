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

import com.vti.dto.ApiResponse;

@RestController
@RequestMapping("/api/v1/positions")
@Validated
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PositionDTO>>> getAll(){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Get all positions successfully", positionService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PositionDTO>> getById(@PathVariable Integer id){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Get position successfully", positionService.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PositionDTO>> save(@Valid @RequestBody PositionFormForCreate form){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(), "Create position successfully", positionService.save(form)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PositionDTO>> update(@PathVariable Integer id, @Valid @RequestBody PositionFormForUpdate form){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Update position successfully", positionService.update(id, form)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Delete position successfully", positionService.delete(id)), HttpStatus.OK);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<String>> deleteByIds(@RequestBody List<Integer> ids){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Bulk delete positions successfully", positionService.deleteByIds(ids)), HttpStatus.OK);
    }
}
