package com.vti.repository.impl;

import com.vti.entity.Account;
import com.vti.repository.IAccountRepository;
import com.vti.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class AccountRepositoryImpl implements IAccountRepository {

    private final SessionFactory sessionFactory = HibernateUtils.sessionFactory;

    @Override
    public List<Account> findAll() {
        Session session = sessionFactory.openSession();
        try {
           String hql = "FROM Account";
           Query<Account> query = session.createQuery(hql, Account.class);
           return query.list();
        } finally {
           session.close();
        }
    }

    @Override
    public Account findById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
           return session.find(Account.class, id);
        } finally {
           session.close();
        }
    }

    @Override
    public void create(Account account) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
           session.persist(account);
           session.getTransaction().commit();
        } catch (Exception e) {
           session.getTransaction().rollback();
           throw e;
        } finally {
           session.close();
        }
    }

    @Override
    public void update(Account account) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
           session.merge(account);
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
           Account account = session.find(Account.class, id);
           if (account != null) {
               session.remove(account);
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