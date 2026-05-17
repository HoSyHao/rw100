package com.vti.backend.repository.impl;

import com.vti.backend.repository.IAccountRepository;
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
        String insert = "insert into `position` (position_name) values (?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insert);
        ) {
            stmt.setString(1, name.trim().toUpperCase());
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
                "SET position_name = ?\n" +
                "WHERE position_id = ?;";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(update);
        ) {
            stmt.setString(1, name.trim().toUpperCase());
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
}
