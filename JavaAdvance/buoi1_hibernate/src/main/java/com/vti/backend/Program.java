package com.vti.backend;

import com.vti.entity.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        // lấy ds department từ DB
        // b1: tạo session kết nối đến DB
//        SessionFactory sessionFactory;
//        Configuration cfg = new Configuration();
//        cfg.configure();
//        sessionFactory = cfg.buildSessionFactory();
//        // kết nối đến DB
//        Session session = sessionFactory.openSession();// tajo connection
//
////        // lấy dữ liệu tu bang department
//        List<Department> departments = new ArrayList<>();
//        String hql = "From Department";
//        Query<Department> query = session.createQuery(hql, Department.class);
//        departments = query.list();// lay ds
//        for (Department de: departments) {
//            System.out.println(de.toString());
//        }
//        String hql = "From Department where id = :idParam";
//        Query<Department> query = session.createQuery(hql, Department.class);
//        query.setParameter("idParam", 1);
//        Department department = query.uniqueResult();
//        System.out.println(department);

        // theem moi 1 department
        // mở transaction
//        session.beginTransaction();
//        try {
//            Department department = new Department();
//            department.setName("Demo123");
//
//            session.persist(department);
//            // commit dữ liệu nếu thành công
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            // hoàn lại dữ liệu nếu gặp lỗi
//            session.getTransaction().rollback();
//        }

        // update tên 'departmentName5' cho depatment có id = 5
        // b1: tìm department có id = 5
//        String hql = "From Department where id = :idParam";
//        Query<Department> query = session.createQuery(hql, Department.class);
//        query.setParameter("idParam", 5);
//        Department departmentUpdate = query.uniqueResult();
//
//        //b2 update thông tin cho department trên
//        session.beginTransaction();
//        departmentUpdate.setName("departmentName5");
//        session.getTransaction().commit();





    }
}