package com.vti.controller;

import com.vti.dto.AccountDTO;
import com.vti.dto.AccountFormForCreate;
import com.vti.dto.AccountFormForUpdate;
import com.vti.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import com.vti.dto.ApiResponse;

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDTO>>> getAll(){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Get all accounts successfully", accountService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDTO>> getById(@PathVariable Integer id){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Get account successfully", accountService.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDTO>> save(@Valid @RequestBody AccountFormForCreate form){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(), "Create account successfully", accountService.save(form)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDTO>> update(@PathVariable Integer id, @Valid @RequestBody AccountFormForUpdate form){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Update account successfully", accountService.update(id, form)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Delete account successfully", accountService.delete(id)), HttpStatus.OK);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<String>> deleteByIds(@RequestBody List<Integer> ids){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Bulk delete accounts successfully", accountService.deleteByIds(ids)), HttpStatus.OK);
    }
}
