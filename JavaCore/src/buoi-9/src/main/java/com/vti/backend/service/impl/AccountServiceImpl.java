package com.vti.backend.service.impl;

import com.vti.backend.repository.IAccountRepository;
import com.vti.backend.repository.impl.AccountRepositoryImpl;
import com.vti.backend.service.IAccountService;
import com.vti.entity.Account;

import java.util.List;

public class AccountServiceImpl implements IAccountService {
    private final IAccountRepository accountRepository = new AccountRepositoryImpl();
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    @Override
    public List<Account> findByFullname(String fullname) {
        return accountRepository.findByFullname(fullname);
    }

    @Override
    public List<Account> findByFullnameAndUsername(String val_ful, String val_user) {
        return accountRepository.findByFullnameAndUsername(val_ful, val_user);
    }

    @Override
    public boolean createAccount(String email, String username, String fullname, Integer departmentId, Integer positionId) {
        return accountRepository.createAccount(email, username, fullname, departmentId, positionId);
    }

    @Override
    public boolean updateAccount(String email, String username, String fullname, Integer departmentId, Integer positionId, int accountId) {
        return accountRepository.updateAccount(email, username, fullname, departmentId, positionId, accountId);
    }

    @Override
    public boolean deleteAccount(int accountId) {
        return accountRepository.deleteAccount(accountId);
    }
}
