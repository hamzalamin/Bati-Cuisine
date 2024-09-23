package com.wora.repositories.impl;

import com.wora.config.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public abstract class AbstractRepository<tableName> {
    protected final Connection connection = JdbcConnection.getInstance().getConnection();
    protected final String tableName;

    public AbstractRepository(String tableName){
        this.tableName = tableName;
    }

    public void delete(UUID id) {
        final String query = "DELETE FROM " + tableName + " WHERE id = ?::uuid ON CASCADE";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
