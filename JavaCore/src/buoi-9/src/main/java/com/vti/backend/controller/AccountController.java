package com.vti.backend.controller;

import com.vti.backend.service.IAccountService;
import com.vti.backend.service.impl.AccountServiceImpl;
import com.vti.entity.Account;

import java.util.List;

public class AccountController {
    private final IAccountService accountService = new AccountServiceImpl();

    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    public List<Account> findByFullname(String fullname) {
        return accountService.findByFullname(fullname);
    }

    public List<Account> findByFullnameAndUsername(String val_ful, String val_user) {
        return accountService.findByFullnameAndUsername(val_ful, val_user);
    }

    public boolean createAccount(String email, String username, String fullname, Integer departmentId, Integer positionId) {
        return accountService.createAccount(email, username, fullname, departmentId, positionId);
    }

    public boolean updateAccount(String email, String username, String fullname, Integer departmentId, Integer positionId, int accountId) {
        return accountService.updateAccount(email, username, fullname, departmentId, positionId, accountId);
    }

    public boolean deleteAccount(int accountId) {
        return accountService.deleteAccount(accountId);
    }

    public boolean checkAccountExists(String email, String username, Integer accountId) {
        return accountService.checkAccountExists(email, username, accountId);
    }

    public com.vti.entity.Account getAccountById(int accountId) {
        return accountService.getAccountById(accountId);
    }

    public String importAccountCSV(String pathName) {
        return accountService.importAccountCSV(pathName);
    }
}
