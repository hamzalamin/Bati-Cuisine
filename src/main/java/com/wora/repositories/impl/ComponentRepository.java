package com.wora.repositories.impl;

import com.wora.config.JdbcConnection;
import com.wora.mapper.IComponentMapper;
import com.wora.models.entities.Client;
import com.wora.models.entities.Component;
import com.wora.models.entities.Project;
import com.wora.models.enums.ComponentType;
import com.wora.models.enums.ProjectStatus;
import com.wora.repositories.IComponentRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class ComponentRepository<Entity, DTO> implements IComponentRepository<DTO> {
    private final Connection connection = JdbcConnection.getInstance().getConnection();
    protected final String tableName;
    protected final IComponentMapper<Entity> mapper;

    protected ComponentRepository(final String tableName, final IComponentMapper<Entity> mapper) {
        this.tableName = tableName;
        this.mapper = mapper;
    }

    @Override
    public List<Component> findAll() {
        final String query = "SELECT * FROM " + tableName;
        final List<Entity> components = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                components.add(mapper.map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    @Override
    public Optional<Component> findByID(UUID id) {
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {

    }
}