package com.vti.controller;

import com.vti.dto.DepartmentDTO;
import com.vti.dto.DepartmentFormForCreate;
import com.vti.dto.DepartmentFormForUpdate;
import com.vti.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/departments")
@Validated
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> findAll(){
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> findById(@PathVariable Integer id){
        return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> save(@Valid @RequestBody DepartmentFormForCreate form){
        return new ResponseEntity<>(departmentService.save(form), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> update(@PathVariable Integer id, @Valid @RequestBody DepartmentFormForUpdate form){
        return new ResponseEntity<>(departmentService.update(id, form), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return new ResponseEntity<>(departmentService.delete(id), HttpStatus.OK);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<String> deleteByIds(@RequestBody List<Integer> ids){
        return new ResponseEntity<>(departmentService.deleteByIds(ids), HttpStatus.OK);
    }
}
