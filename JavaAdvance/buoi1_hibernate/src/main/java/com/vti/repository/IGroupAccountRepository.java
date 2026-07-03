package com.vti.repository;

import com.vti.entity.GroupAccount;

import java.util.List;

public interface IGroupAccountRepository {
    List<GroupAccount> findAll();
    GroupAccount findById(Integer id);
    void create(GroupAccount groupAccount);
    void update(GroupAccount groupAccount);
    void delete(Integer id);
    List<GroupAccount> findByAccountId(Integer accountId);
    List<GroupAccount> findByGroupId(Integer groupId);
    void deleteByAccountAndGroup(Integer accountId, Integer groupId);
}
