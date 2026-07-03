package com.vti.repository.impl;

import com.vti.entity.GroupAccount;
import com.vti.repository.IGroupAccountRepository;
import com.vti.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GroupAccountRepositoryImpl implements IGroupAccountRepository {
    private final SessionFactory sessionFactory = HibernateUtils.sessionFactory;

    @Override
    public List<GroupAccount> findAll() {
        List<GroupAccount> groupAccounts = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM GroupAccount";
            Query<GroupAccount> query = session.createQuery(hql, GroupAccount.class);
            groupAccounts = query.list();
        } finally {
            session.close();
        }
        return groupAccounts;
    }

    @Override
    public GroupAccount findById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return session.find(GroupAccount.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public void create(GroupAccount groupAccount) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(groupAccount);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(GroupAccount groupAccount) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.merge(groupAccount);
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
            GroupAccount groupAccount = session.find(GroupAccount.class, id);
            if (groupAccount != null) {
                session.remove(groupAccount);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<GroupAccount> findByAccountId(Integer accountId) {
        List<GroupAccount> groupAccounts = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM GroupAccount WHERE account.id = :accountId";
            Query<GroupAccount> query = session.createQuery(hql, GroupAccount.class);
            query.setParameter("accountId", accountId);
            groupAccounts = query.list();
        } finally {
            session.close();
        }
        return groupAccounts;
    }

    @Override
    public List<GroupAccount> findByGroupId(Integer groupId) {
        List<GroupAccount> groupAccounts = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM GroupAccount WHERE group.id = :groupId";
            Query<GroupAccount> query = session.createQuery(hql, GroupAccount.class);
            query.setParameter("groupId", groupId);
            groupAccounts = query.list();
        } finally {
            session.close();
        }
        return groupAccounts;
    }

    @Override
    public void deleteByAccountAndGroup(Integer accountId, Integer groupId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            String hql = "DELETE FROM GroupAccount WHERE account.id = :accountId AND group.id = :groupId";
            Query<?> query = session.createQuery(hql);
            query.setParameter("accountId", accountId);
            query.setParameter("groupId", groupId);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
