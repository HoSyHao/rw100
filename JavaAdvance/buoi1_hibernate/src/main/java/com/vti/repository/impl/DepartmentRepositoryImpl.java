package com.vti.repository.impl;

import com.vti.entity.Department;
import com.vti.repository.IDepartmentRepository;
import com.vti.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements IDepartmentRepository {
    private final SessionFactory sessionFactory = HibernateUtils.sessionFactory;

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Department";
            Query<Department> query = session.createQuery(hql, Department.class);
            departments = query.list();
        } finally {
            session.close();
        }
        return departments;
    }

    @Override
    public Department findById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return session.find(Department.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public void create(Department department) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(department);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Department department) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.merge(department);
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
            Department department = session.find(Department.class, id);
            if (department != null) {
                session.remove(department);
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