package com.vti.backend.repository.impl;

import com.vti.backend.repository.IDepartmentRepository;
import com.vti.entity.Department;
import com.vti.utils.JDBCUtils;

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

    @Override
    public boolean createDepartments(List<Department> departments) {
        String insert = "insert into `department` (department_name) values (?);";
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            // 1. Tắt chế độ tự động commit để bắt đầu Transaction
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                for (Department dep : departments) {
                    stmt.setString(1, dep.getDepartmentName());
                    stmt.addBatch();
                }
                // 2. Thực thi batch
                stmt.executeBatch();

                // 3. Commit tất cả nếu không có lỗi
                conn.commit();
                return true;
            } catch (Exception e) {
                // 4. Nếu có bất kỳ lỗi nào, rollback (hủy bỏ) toàn bộ
                if (conn != null) conn.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Luôn bật lại autoCommit hoặc đóng connection
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
        return false;
    }

    @Override
    public List<String> findExistingNames(List<String> names) {
        List<String> existingNames = new ArrayList<>();
        if (names == null || names.isEmpty()) return existingNames;

        StringBuilder query = new StringBuilder("SELECT department_name FROM department WHERE department_name IN (");
        for (int i = 0; i < names.size(); i++) {
            query.append("?");
            if (i < names.size() - 1) query.append(",");
        }
        query.append(")");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < names.size(); i++) {
                stmt.setString(i + 1, names.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    existingNames.add(rs.getString("department_name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existingNames;
    }

    @Override
    public List<Integer> findExistingIds(List<Integer> ids) {
        List<Integer> existingIds = new ArrayList<>();
        if (ids == null || ids.isEmpty()) return existingIds;

        StringBuilder query = new StringBuilder("SELECT department_id FROM department WHERE department_id IN (");
        for (int i = 0; i < ids.size(); i++) {
            query.append("?");
            if (i < ids.size() - 1) query.append(",");
        }
        query.append(")");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < ids.size(); i++) {
                stmt.setInt(i + 1, ids.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    existingIds.add(rs.getInt("department_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existingIds;
    }

}
