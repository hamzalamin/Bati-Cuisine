package com.wora.repositories.impl;

import com.wora.config.JdbcConnection;
import com.wora.models.dtos.ProjectDto;
import com.wora.models.entities.Client;
import com.wora.models.entities.Project;
import com.wora.models.enums.ProjectStatus;
import com.wora.repositories.IProjectRepository;

import java.sql.*;
import java.util.*;

public class ProjectRepository extends AbstractRepository<Project> implements IProjectRepository {

    public ProjectRepository() {
        super("projects");
    }
    @Override
    public List<Project> findAll() {
        final String query = "SELECT * FROM " + tableName + " INNER JOIN clients ON clients.id = projects.client_id";
        final List<Project> projects = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Project project = new Project(
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
                );
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }

    @Override
    public Optional<Project> findById(UUID id) {
        final String query = "SELECT * FROM " + tableName + " t INNER JOIN clients c ON c.id = t.client_id WHERE t.id = ?::uuid";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            final ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Project project = new Project(
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
                );
                return Optional.ofNullable(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void create(ProjectDto dto) {
        final String query = "INSERT INTO " + tableName + "(id, project_name, profit_margin, total_cost, project_status, client_id) " +
                "VALUES (?::uuid, ?, ?, ?, ?::project_status, ?::uuid)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int c = 1;
            stmt.setObject(c++, UUID.randomUUID());
            stmt.setString(c++, dto.projectName());
            stmt.setDouble(c++, dto.profitMargin());
            stmt.setDouble(c++, dto.totalCost());
            stmt.setObject(c++, dto.projectStatus().toString().toUpperCase());
            stmt.setObject(c++, dto.client_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ProjectDto dto, UUID id) {
        final String query = """
                UPDATE projects
                SET
                project_name = ?,
                profit_margin = ?,
                total_cost = ?,
                project_status = ?::project_status,
                client_id = ?::uuid
                WHERE id = ?::uuid
                """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int c = 1;
            stmt.setString(c++, dto.projectName());
            stmt.setDouble(c++, dto.profitMargin());
            stmt.setDouble(c++, dto.totalCost());
            stmt.setObject(c++, dto.projectStatus().toString().toUpperCase());
            stmt.setObject(c++, dto.client_id());
            stmt.setObject(c++, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
