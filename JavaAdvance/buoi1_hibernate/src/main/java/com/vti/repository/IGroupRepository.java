package com.vti.repository;

import com.vti.entity.Group;

import java.util.List;

public interface IGroupRepository {
    List<Group> findAll();
    Group findById(Integer id);
    void create(Group group);
    void update(Group group);
    void delete(Integer id);
}