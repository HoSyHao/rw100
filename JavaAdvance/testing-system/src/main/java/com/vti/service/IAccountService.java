package com.vti.service;

import com.vti.dto.AccountDTO;
import com.vti.dto.AccountFormForCreate;
import com.vti.dto.AccountFormForUpdate;

import java.util.List;

public interface IAccountService {
    public List<AccountDTO> findAll();
    public AccountDTO findById(Integer id);
    public AccountDTO save(AccountFormForCreate form);
    public AccountDTO update(Integer id, AccountFormForUpdate form);
    public String delete(Integer id);
    public String deleteByIds(List<Integer> ids);
}
