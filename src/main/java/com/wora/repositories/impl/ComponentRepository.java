package com.wora.repositories.impl;

import com.wora.config.JdbcConnection;
import com.wora.mapper.IComponentMapper;
import com.wora.models.entities.Component;
import com.wora.repositories.IComponentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class ComponentRepository<Entity, DTO> implements IComponentRepository<Entity, DTO> {
    private final Connection connection = JdbcConnection.getInstance().getConnection();
    protected final String tableName;
    protected final IComponentMapper<Entity> mapper;

    protected ComponentRepository(final String tableName, final IComponentMapper<Entity> mapper) {
        this.tableName = tableName;
        this.mapper = mapper;
    }

    @Override
    public List<Entity> findAll() {
        final String query = "SELECT * FROM " + tableName + " t INNER JOIN projects ON projects.id = t.project_id INNER JOIN clients ON clients.id = projects.client_id";
        final List<Entity> components = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                components.add(mapper.map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return components;
    }

    @Override
    public Optional<Entity> findByID(UUID id) {

        final String query = "SELECT * FROM " + tableName + " t INNER JOIN projects ON projects.id = t.project_id INNER JOIN clients ON clients.id = projects.client_id  WHERE t.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapper.map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching component by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {
        final String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting component", e);
        }
    }

    public List<Entity> findAllByProjectId(UUID id) {
        final List<Entity> components = new ArrayList<>();
        final String query = "SELECT * FROM  " + tableName + " WHERE project_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                components.add(mapper.mapProjectLess(rs));
            }
            return components;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
