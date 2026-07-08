package com.vti.service.impl;

import com.vti.dto.AccountDTO;
import com.vti.dto.AccountFormForCreate;
import com.vti.dto.AccountFormForUpdate;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;
import com.vti.exception.ResourceNotFoundException;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.repository.IPositionRepository;
import com.vti.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private IPositionRepository positionRepository;

    @Override
    public List<AccountDTO> findAll() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> dtos = new ArrayList<>();
        for (Account entity : accounts) {
            dtos.add(mapToDTO(entity));
        }
        return dtos;
    }

    @Override
    public AccountDTO findById(Integer id) {
        Account entity = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return mapToDTO(entity);
    }

    @Override
    public AccountDTO save(AccountFormForCreate form) {
        if (accountRepository.existsByUsername(form.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (accountRepository.existsByEmail(form.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Account entity = new Account();
        entity.setEmail(form.getEmail());
        entity.setUsername(form.getUsername());
        entity.setFullName(form.getFullName());

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

        Account savedEntity = accountRepository.save(entity);
        return mapToDTO(savedEntity);
    }

    @Override
    public AccountDTO update(Integer id, AccountFormForUpdate form) {
        Account entity = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        if (form.getEmail() != null && !form.getEmail().equals(entity.getEmail())) {
            if (accountRepository.existsByEmail(form.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            entity.setEmail(form.getEmail());
        }

        if (form.getUsername() != null && !form.getUsername().equals(entity.getUsername())) {
            if (accountRepository.existsByUsername(form.getUsername())) {
                throw new RuntimeException("Username already exists");
            }
            entity.setUsername(form.getUsername());
        }

        if (form.getFullName() != null) entity.setFullName(form.getFullName());

        if (form.getDepartmentId() != null) {
            Department department = departmentRepository.findById(form.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + form.getDepartmentId()));
            entity.setDepartment(department);
        } else {
            entity.setDepartment(null);
        }

        if (form.getPositionId() != null) {
            Position position = positionRepository.findById(form.getPositionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: " + form.getPositionId()));
            entity.setPosition(position);
        } else {
            entity.setPosition(null);
        }

        Account updatedEntity = accountRepository.save(entity);
        return mapToDTO(updatedEntity);
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
        accountRepository.deleteAllByIdInBatch(ids);
        return "Deleted " + ids.size() + " accounts successfully";
    }

    private AccountDTO mapToDTO(Account entity) {
        AccountDTO dto = new AccountDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setFullName(entity.getFullName());
        if (entity.getDepartment() != null) dto.setDepartmentName(entity.getDepartment().getName());
        if (entity.getPosition() != null) dto.setPositionName(entity.getPosition().getName().toString());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }
}
