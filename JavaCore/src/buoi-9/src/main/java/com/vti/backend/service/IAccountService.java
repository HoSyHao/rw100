package com.vti.backend.service;

import com.vti.entity.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAllAccounts();
    List<Account> findByFullname(String fullname);
    List<Account> findByFullnameAndUsername(String val_ful, String val_user);
    boolean createAccount(String email, String username, String fullname, Integer departmentId, Integer positionId);
    boolean updateAccount(String email, String username, String fullname, Integer departmentId, Integer positionId, int accountId);
    boolean deleteAccount(int accountId);
}
