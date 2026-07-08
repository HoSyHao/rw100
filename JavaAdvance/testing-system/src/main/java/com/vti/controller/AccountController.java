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

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> findAll(){
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Integer id){
        return new ResponseEntity<>(accountService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> save(@RequestBody AccountFormForCreate form){
        return new ResponseEntity<>(accountService.save(form), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Integer id, @RequestBody AccountFormForUpdate form){
        return new ResponseEntity<>(accountService.update(id, form), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return new ResponseEntity<>(accountService.delete(id), HttpStatus.OK);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<String> deleteByIds(@RequestBody List<Integer> ids){
        return new ResponseEntity<>(accountService.deleteByIds(ids), HttpStatus.OK);
    }
}
