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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import com.vti.dto.ApiResponse;

@RestController
@RequestMapping("/api/v1/departments")
@Validated
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentDTO>>> findAll(){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Get all departments successfully", departmentService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDTO>> findById(@PathVariable Integer id){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Get department successfully", departmentService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse<DepartmentDTO>> findByName(@RequestParam String name){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Get department successfully", departmentService.findByName(name)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentDTO>> save(@Valid @RequestBody DepartmentFormForCreate form){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(), "Create department successfully", departmentService.save(form)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDTO>> update(@PathVariable Integer id, @Valid @RequestBody DepartmentFormForUpdate form){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Update department successfully", departmentService.update(id, form)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Delete department successfully", departmentService.delete(id)), HttpStatus.OK);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<String>> deleteByIds(@RequestBody List<Integer> ids){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Bulk delete departments successfully", departmentService.deleteByIds(ids)), HttpStatus.OK);
    }
}
