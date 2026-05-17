package com.vti.backend.repository.impl;

import com.vti.backend.repository.IAccountRepository;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;
import com.vti.enums.PositionName;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.vti.utils.JDBCUtils.getConnection;

public class AccountRepositoryImpl implements IAccountRepository {
    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "select * from `account` a\n" +
                "left join `department` d ON d.department_id = a.department_id\n" +
                "left join `position` p ON p.position_id = a.position_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("account_id");
                    String email = rs.getString("email");
                    String username = rs.getString("username");
                    String fullname = rs.getString("full_name");

                    Integer departmentId = (Integer) rs.getObject("department_id");
                    Integer positionId = (Integer) rs.getObject("position_id");

                    LocalDateTime createDate = (LocalDateTime) rs.getObject("create_date");

                    Position position = null;
                    Department department = null;

                    //Kiem tra chua co id thi khong tao pos va dept
                    if (departmentId != null) {
                        String departmentName = rs.getString("department_name");
                        department = new Department(departmentId, departmentName);
                    }
                    if (positionId != null) {
                        String positionNameStr = rs.getString("position_name");
                        PositionName positionName = PositionName.valueOf(positionNameStr);
                        position = new Position(positionId, positionName);
                    }
                    Account account = new Account(id, email, username, fullname, department, position, createDate);
                    accounts.add(account);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public List<Account> findByFullname(String fullname) {
        ArrayList<Account> accounts = new ArrayList<>();
        String query = "select * from `account` a\n" +
                "left join `department` d ON d.department_id = a.department_id\n" +
                "left join `position` p ON p.position_id = a.position_id\n" +
                "where lower(a.full_name) like lower(?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, "%" + fullname + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("account_id");
                    String email = rs.getString("email");
                    String username = rs.getString("username");
                    String full_name = rs.getString("full_name");

                    Integer departmentId = (Integer) rs.getObject("department_id");
                    Integer positionId = (Integer) rs.getObject("position_id");

                    LocalDateTime createDate = (LocalDateTime) rs.getObject("create_date");

                    Position position = null;
                    Department department = null;

                    //Kiem tra chua co id thi khong tao pos va dept
                    if (departmentId != null) {
                        String departmentName = rs.getString("department_name");
                        department = new Department(departmentId, departmentName);
                    }
                    if (positionId != null) {
                        String positionNameStr = rs.getString("position_name");
                        PositionName positionName = PositionName.valueOf(positionNameStr);
                        position = new Position(positionId, positionName);
                    }
                    Account account = new Account(id, email, username, full_name, department, position, createDate);
                    accounts.add(account);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public List<Account> findByFullnameAndUsername(String val_ful, String val_user) {
        ArrayList<Account> accounts = new ArrayList<>();
        String query = "select * from `account` a\n" +
                "left join `department` d ON d.department_id = a.department_id\n" +
                "left join `position` p ON p.position_id = a.position_id\n" +
                "where lower(full_name) like lower(?) \n" +
                "and lower(username) like lower(?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, "%" + val_ful + "%");
            stmt.setString(2, "%" + val_user + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("account_id");
                    String email = rs.getString("email");
                    String username = rs.getString("username");
                    String fullname = rs.getString("full_name");

                    Integer departmentId = (Integer) rs.getObject("department_id");
                    Integer positionId = (Integer) rs.getObject("position_id");

                    LocalDateTime createDate = (LocalDateTime) rs.getObject("create_date");

                    Position position = null;
                    Department department = null;

                    //Kiem tra chua co id thi khong tao pos va dept
                    if (departmentId != null) {
                        String departmentName = rs.getString("department_name");
                        department = new Department(departmentId, departmentName);
                    }
                    if (positionId != null) {
                        String positionNameStr = rs.getString("position_name");
                        PositionName positionName = PositionName.valueOf(positionNameStr);
                        position = new Position(positionId, positionName);
                    }
                    Account account = new Account(id, email, username, fullname, department, position, createDate);
                    accounts.add(account);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean createAccount(String email, String username, String fullname, Integer departmentId, Integer positionId) {
        String insert = "insert into `account` (\n" +
                "\temail,\n" +
                "    username,\n" +
                "    full_name,\n" +
                "    department_id,\n" +
                "    position_id) \n" +
                "    values \n" +
                "    (?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
        ) {
            stmt.setString(1, email.trim());
            stmt.setString(2, username.trim());
            stmt.setString(3, fullname.trim());
            if (departmentId == null) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, departmentId);
            }

            if (positionId == null) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, positionId);
            }

            int rows = stmt.executeUpdate();
            return (rows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAccount(String email, String username, String fullname, Integer departmentId, Integer positionId, int accountId) {
        String update = """
                UPDATE account
                SET email = COALESCE(?, email),
                    username = COALESCE(?, username),
                    full_name = COALESCE(?, full_name),
                    department_id = COALESCE(?, department_id),
                    position_id = COALESCE(?, position_id)
                WHERE account_id = ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(update)) {

            // nếu rỗng -> null
            stmt.setString(1, email.isBlank() ? null : email.trim());
            stmt.setString(2, username.isBlank() ? null : username.trim());
            stmt.setString(3, fullname.isBlank() ? null : fullname.trim());

            // Integer mới set null được
            if (departmentId == null) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, departmentId);
            }

            if (positionId == null) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, positionId);
            }

            stmt.setInt(6, accountId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteAccount(int accountId) {
        String delelte = "delete from `account` \n" +
                "where account_id = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(delelte);
        ) {
            stmt.setInt(1, accountId);
            int rows = stmt.executeUpdate();
            return (rows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
