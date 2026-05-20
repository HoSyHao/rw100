package com.vti.backend.repository.impl;

import com.vti.backend.repository.IDepartmentRepository;
import com.vti.entity.Department;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.vti.utils.JDBCUtils.getConnection;

public class DepartmentRepositoryImpl implements IDepartmentRepository {

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departmentList = new ArrayList<>();
        String query = "SELECT * FROM `department`";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) { // Excute sql statement then assign result into rs
                while (rs.next()) { // Loop through each line of rs
                    int id = rs.getInt("department_id"); // Get value from column department_id
                    String name = rs.getString("department_name"); // Get value from column department_name
                    Department department = new Department(id, name);
                    departmentList.add(department);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentList;
    }

    @Override
    public List<Department> findDepartmentByIdAndName(String searchName, int searchId) {
        List<Department> departmentList = new ArrayList<>();
        String query = "SELECT * FROM `department` WHERE LOWER(department_name) LIKE ? AND department_id = ? ";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)
        ) {

            stmt.setString(1, "%" + searchName.toLowerCase().trim() + "%"); //Assign parm to ? 1
            stmt.setInt(2, searchId); //Assign parm to ? 2

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) { // Loop through each line of rs
                    int id = rs.getInt("department_id"); // Get value from column department_id
                    String name = rs.getString("department_name"); // Get value from column department_name
                    Department department = new Department(id, name);
                    departmentList.add(department);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departmentList;
    }

    @Override
    public List<Department> findeDepartmentHaveMoreThan2Acc() {
        List<Department> departmentList = new ArrayList<>();
        String query = "SELECT d.*,\n" +
                "       COUNT(1) AS total_acc,\n" +
                "\t   GROUP_CONCAT(a.account_id) AS accounts\n" +
                "FROM `account` a\n" +
                "JOIN `department` d ON d.department_id = a.department_id\n" +
                "GROUP BY d.department_id\n" +
                "HAVING total_acc >= 2";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) { // Loop through each line of rs
                    int id = rs.getInt("department_id"); // Get value from column department_id
                    String name = rs.getString("department_name"); // Get value from column department_name
                    Department department = new Department(id, name);
                    departmentList.add(department);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return departmentList;
    }

    @Override
    public boolean createDepartment(String departmentName) {
        String insert = "insert into `department` (department_name) values (?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
        ) {
            stmt.setString(1, departmentName);
            int rows = stmt.executeUpdate(); // Number of lines affacted in db
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDepartment(int id) {
        String delete = "delete from `department` where department_id = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(delete);
        ) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate(); // Number of lines affacted in db
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateDepartment(int id, String departmentName) {
        String update = "UPDATE department\n" +
                "SET department_name = ?\n" +
                "WHERE department_id = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(update);
        ) {
            stmt.setString(1, departmentName);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate(); // Number of lines affacted in db
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkExistDepartment(String departmentName, Integer departmentId) {
        String query = " SELECT COUNT(1) FROM department " +
                "WHERE (LOWER(department_name) = LOWER(?) AND ? IS NULL) " +
                "   OR (department_id = ? AND ? IS NULL) " +
                "   OR (LOWER(department_name) = LOWER(?) AND department_id <> ?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setObject(1, departmentName);
            stmt.setObject(2, departmentId);

            stmt.setObject(3, departmentId);
            stmt.setObject(4, departmentName);

            stmt.setObject(5, departmentName);
            stmt.setObject(6, departmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
