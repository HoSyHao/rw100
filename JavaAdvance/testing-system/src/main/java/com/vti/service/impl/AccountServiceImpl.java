package com.vti.service.impl;

import com.vti.dto.AccountDTO;
import com.vti.dto.AccountFormForCreate;
import com.vti.dto.AccountFormForUpdate;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;
import com.vti.exception.DuplicateDataException;
import com.vti.exception.ResourceNotFoundException;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.repository.IPositionRepository;
import com.vti.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private IPositionRepository positionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream().map(account -> modelMapper.map(account, AccountDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO findById(Integer id) {
        Account entity = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return modelMapper.map(entity, AccountDTO.class);
    }

    @Override
    public AccountDTO save(AccountFormForCreate form) {
        if (accountRepository.existsByUsername(form.getUsername())) {
            throw new DuplicateDataException("Username already exists");
        }
        if (accountRepository.existsByEmail(form.getEmail())) {
            throw new DuplicateDataException("Email already exists");
        }

        Department department = departmentRepository.findById(form.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + form.getDepartmentId()));

        Position position = positionRepository.findById(form.getPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + form.getPositionId()));

        Account entity = new Account();
        entity.setEmail(form.getEmail());
        entity.setUsername(form.getUsername());
        entity.setFullName(form.getFullName());
        entity.setDepartment(department);
        entity.setPosition(position);

        Account savedEntity = accountRepository.save(entity);
        return modelMapper.map(savedEntity, AccountDTO.class);
    }

    @Override
    public AccountDTO update(Integer id, AccountFormForUpdate form) {
        Account entity = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        if (form.getEmail() != null && !form.getEmail().equals(entity.getEmail())) {
            if (accountRepository.existsByEmail(form.getEmail())) {
                throw new DuplicateDataException("Email already exists");
            }
            entity.setEmail(form.getEmail());
        }

        if (form.getUsername() != null && !form.getUsername().equals(entity.getUsername())) {
            if (accountRepository.existsByUsername(form.getUsername())) {
                throw new DuplicateDataException("Username already exists");
            }
            entity.setUsername(form.getUsername());
        }

        if (form.getFullName() != null) {
            entity.setFullName(form.getFullName());
        }

        if (form.getDepartmentId() != null) {
            Department department = departmentRepository.findById(form.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + form.getDepartmentId()));
            entity.setDepartment(department);
        }

        if (form.getPositionId() != null) {
            Position position = positionRepository.findById(form.getPositionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + form.getPositionId()));
            entity.setPosition(position);
        }

        Account updatedEntity = accountRepository.save(entity);
        return modelMapper.map(updatedEntity, AccountDTO.class);
    }

    @Override
    public String delete(Integer id) {
        if(!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
        return "Account with id " + id + " has been deleted successfully";
    }

    @Override
    public String deleteByIds(List<Integer> ids) {
        int count = accountRepository.customDeleteByIds(ids);
        return "Deleted " + count + " accounts successfully";
    }
}
