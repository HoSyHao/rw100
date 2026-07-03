package com.vti.repository.impl;

import com.vti.entity.Position;
import com.vti.enums.PositionName;
import com.vti.repository.IPositionRepository;
import com.vti.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PositionRepositoryImpl implements IPositionRepository {
    private final SessionFactory sessionFactory = HibernateUtils.sessionFactory;

    @Override
    public List<Position> findAll() {
        List<Position> positions = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Position";
            Query<Position> query = session.createQuery(hql, Position.class);
            positions = query.list();
        } finally {
            session.close();
        }
        return positions;
    }

    @Override
    public Position findById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return session.find(Position.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public void create(Position position) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(position);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Position position) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.merge(position);
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
            Position position = session.find(Position.class, id);
            if (position != null) {
                session.remove(position);
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