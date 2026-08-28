package repository;

import config.DatabaseConnection;
import com.luca.football.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTeamRepository implements TeamRepository {

    @Override
    public Team save(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }

        String sql = "INSERT INTO Teams (Name, City, ShortCode) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, team.getName());
            stmt.setString(2, team.getCity());
            stmt.setString(3, team.getShortCode());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long generatedId = generatedKeys.getLong(1);
                    return new Team(generatedId, team.getName(), team.getCity(), team.getShortCode());
                } else {
                    throw new SQLException("Creating team failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving team: " + team.getName(), e);
        }
    }

    @Override
    public Optional<Team> findById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        String sql = "SELECT Id, Name, City, ShortCode FROM Teams WHERE Id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToTeam(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying team by id: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Team> findByShortCode(String shortCode) {
        if (shortCode == null || shortCode.isBlank()) {
            return Optional.empty();
        }

        String sql = "SELECT Id, Name, City, ShortCode FROM Teams WHERE ShortCode = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shortCode);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToTeam(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error querying team by shortCode: " + shortCode, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT Id, Name, City, ShortCode FROM Teams ORDER BY Name ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                teams.add(mapResultSetToTeam(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all teams", e);
        }

        return teams;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null || id <= 0) {
            return false;
        }

        String sql = "DELETE FROM Teams WHERE Id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting team with id: " + id, e);
        }
    }

    private Team mapResultSetToTeam(ResultSet rs) throws SQLException {
        return new Team(
                rs.getLong("Id"),
                rs.getString("Name"),
                rs.getString("City"),
                rs.getString("ShortCode")
        );
    }
}