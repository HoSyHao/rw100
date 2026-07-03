package com.vti.repository.impl;

import com.vti.entity.Group;
import com.vti.repository.IGroupRepository;
import com.vti.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements IGroupRepository {
    private final SessionFactory sessionFactory = HibernateUtils.sessionFactory;

    @Override
    public List<Group> findAll() {
        List<Group> groups = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Group";
            Query<Group> query = session.createQuery(hql, Group.class);
            groups = query.list();
        } finally {
            session.close();
        }
        return groups;
    }

    @Override
    public Group findById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return session.find(Group.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public void create(Group group) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(group);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Group group) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.merge(group);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Group group = session.find(Group.class, id);
            if (group != null) {
                session.remove(group);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
