package com.wora.repositories.impl;

import com.wora.config.JdbcConnection;
import com.wora.models.dtos.EstimateDto;
import com.wora.models.entities.Client;
import com.wora.models.entities.Estimate;
import com.wora.models.entities.Project;
import com.wora.models.enums.ProjectStatus;
import com.wora.repositories.IEstimateRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EstimateRepository extends AbstractRepository<Estimate> implements IEstimateRepository {

    public EstimateRepository() {
        super("estimates");
    }

    @Override
    public List<Estimate> findAll() {
        final String query = "SELECT * FROM " +tableName + " e INNER JOIN projects p ON p.id = e.project_id INNER JOIN clients c ON c.id = p.client_id";
        final List<Estimate> estimates = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Estimate estimate = new Estimate(
                        UUID.fromString(rs.getString("id")),
                        rs.getDouble("estimated_amount"),
                        rs.getTimestamp("issue_date").toLocalDateTime(),
                        rs.getTimestamp("validity_date").toLocalDateTime(),
                        rs.getBoolean("is_accepted"),
                        new Project (
                                UUID.fromString(rs.getString("id")),
                                rs.getString("project_name"),
                                rs.getDouble("profit_margin"),
                                rs.getDouble("total_cost"),
                                ProjectStatus.valueOf(rs.getString("project_status").toUpperCase()),
                                new Client(
                                        UUID.fromString(rs.getString("id")),
                                        rs.getString("name"),
                                        rs.getString("address"),
                                        rs.getString("phone"),
                                        rs.getBoolean("is_professional")
                                )
                        )
                );
                estimates.add(estimate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return estimates;
    }

    @Override
    public Optional<Estimate> findById(UUID id) {
        final String query = "SELECT * FROM " + tableName + " e INNER JOIN projects p ON p.id = e.project_id INNER JOIN clients c ON c.id = p.client_id WHERE id = ?::uuid";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            final ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Estimate estimate = new Estimate(
                        UUID.fromString(rs.getString("id")),
                        rs.getDouble("estimated_amount"),
                        rs.getTimestamp("issue_date").toLocalDateTime(),
                        rs.getTimestamp("validity_date").toLocalDateTime(),
                        rs.getBoolean("is_accepted"),
                        new Project (
                                UUID.fromString(rs.getString("id")),
                                rs.getString("project_name"),
                                rs.getDouble("profit_margin"),
                                rs.getDouble("total_cost"),
                                ProjectStatus.valueOf(rs.getString("project_status").toUpperCase()),
                                new Client(
                                        UUID.fromString(rs.getString("id")),
                                        rs.getString("name"),
                                        rs.getString("address"),
                                        rs.getString("phone"),
                                        rs.getBoolean("is_professional")
                                )
                        )
                );
            return Optional.ofNullable(estimate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Estimate> findClientEstimate(UUID clientId) {
        final String query = "SELECT * FROM " + tableName + " e " +
                "INNER JOIN projects p ON p.id = e.project_id " +
                "INNER JOIN clients c ON c.id = p.client_id " +
                "WHERE p.client_id = ?::uuid";
        final List<Estimate> estimates = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, clientId);
            final ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Estimate estimate = new Estimate(
                        UUID.fromString(rs.getString("e.id")),
                        rs.getDouble("estimated_amount"),
                        rs.getTimestamp("issue_date").toLocalDateTime(),
                        rs.getTimestamp("validity_date").toLocalDateTime(),
                        rs.getBoolean("is_accepted"),
                        new Project(
                                UUID.fromString(rs.getString("p.id")),
                                rs.getString("project_name"),
                                rs.getDouble("profit_margin"),
                                rs.getDouble("total_cost"),
                                ProjectStatus.valueOf(rs.getString("project_status").toUpperCase()),
                                new Client(
                                        UUID.fromString(rs.getString("c.id")),
                                        rs.getString("name"),
                                        rs.getString("address"),
                                        rs.getString("phone"),
                                        rs.getBoolean("is_professional")
                                )
                        )
                );
                estimates.add(estimate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return estimates;
    }



    @Override
    public void create(EstimateDto dto) {
        final String query = "INSERT INTO " + tableName + " (id, estimated_amount, issue_date, validity_date, is_accept) VALUE (?::uuid, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            int c = 1;
            stmt.setObject(c++, UUID.randomUUID());
            stmt.setDouble(c++, dto.estimatedAmount());
            stmt.setTimestamp(c++, Timestamp.valueOf(dto.issueDate()));
            stmt.setTimestamp(c++, Timestamp.valueOf(dto.validityDate()));
            stmt.setBoolean(c++, dto.isAccept());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(EstimateDto dto, UUID id) {
        final String query = "UPDATE INTO " + tableName +
                "SET estimated_amount = ?, " +
                "issue_date = ?, validity_date = ?, is_accept = ?" +
                "WHERE id = ?::uuid";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            int c = 1;
            stmt.setDouble(c++, dto.estimatedAmount());
            stmt.setTimestamp(c++, Timestamp.valueOf(dto.issueDate()));
            stmt.setTimestamp(c++, Timestamp.valueOf(dto.validityDate()));
            stmt.setBoolean(c++, dto.isAccept());
            stmt.setObject(c++, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
