package com.vti.backend.repository.impl;

import com.vti.backend.repository.IPositionRepository;
import com.vti.entity.Position;
import com.vti.enums.PositionName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.vti.utils.JDBCUtils.getConnection;

public class PositionRepositoryImpl implements IPositionRepository {
    @Override
    public List<Position> getAllPositions() {
        String query = "select * from position";
        List<Position> positions = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
        ) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("position_id");
                    PositionName name = PositionName.valueOf(rs.getString("position_name"));
                    Position position = new Position(id, name);
                    positions.add(position);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    @Override
    public List<Position> findByPositionName(String name) {
        List<Position> positions = new ArrayList<>();
        String query = "select * from position where position_name like upper(?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, "%" + name.trim() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("position_id");
                    PositionName positionName = PositionName.valueOf(rs.getString("position_name"));
                    Position position = new Position(id, positionName);
                    positions.add(position);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    @Override
    public boolean createPosition(String name) {
        String insert = "insert into `position` (position_name) values UPPER(?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
        ) {
            stmt.setString(1, name);
            int rows = stmt.executeUpdate();
            return (rows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePosition(String name, int id) {
        String update = "UPDATE `position`\n" +
                "SET position_name = UPPER(?)\n" +
                "WHERE position_id = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(update);
        ) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            return (rows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePosition(int id) {
        String delelte = "delete from `position` where position_name = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(delelte);
        ) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return (rows > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkExistPosition(String name, Integer id) {
        String query = " SELECT COUNT(1) FROM position " +
                "WHERE (position_name = UPPER(?) AND ? IS NULL) " +
                "   OR (position_id = ? AND ? IS NULL) " +
                "   OR (position_name = UPPER(?) AND position_id <> ?);";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setObject(1, name);
            stmt.setObject(2, id);

            stmt.setObject(3, id);
            stmt.setObject(4, name);

            stmt.setObject(5, name);
            stmt.setObject(6, id);

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
    public List<Integer> findExistingIds(List<Integer> ids) {
        List<Integer> existingIds = new ArrayList<>();
        if (ids == null || ids.isEmpty()) return existingIds;

        StringBuilder query = new StringBuilder("SELECT position_id FROM position WHERE position_id IN (");
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
                    existingIds.add(rs.getInt("position_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existingIds;
    }
}
