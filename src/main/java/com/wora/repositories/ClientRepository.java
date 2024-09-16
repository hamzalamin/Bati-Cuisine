package com.wora.repositories;

import com.wora.config.JdbcConnection;
import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository implements IClientRepository {
    private final Connection connection = JdbcConnection.getInstance().getConnection();
    private final String tableName = "clients";

    @Override
    public List<Client> findAll() {
        final String query = "SELECT * FROM " +tableName;
        final List<Client> clients = new ArrayList<>();
        try (Statement stmt = connection.createStatement()){
            final ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Client client = new Client(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("is_professional")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    @Override
    public Optional<Client> findById(UUID id) {
        final String query = "SELECT * FROM " + tableName + " WHERE id = ?::uuid";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            final ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("is_professional")
                );
                 return Optional.ofNullable(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public void create(ClientDto dto) {
        final String query = "INSERT INTO " + tableName + " (id, name, address, phone, is_professional)" +
                " VALUES (?::uuid, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            int count = 1;
            stmt.setObject(count++, UUID.randomUUID());
            stmt.setString(count++, dto.name());
            stmt.setString(count++, dto.address());
            stmt.setString(count++, dto.phone());
            stmt.setBoolean(count++, dto.isProfessional());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ClientDto dto, UUID id) {
        final String query = "UPDATE " + tableName +
                " SET name = ?, address = ?, phone = ?, is_professional = ? WHERE id = ?::uuid";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int count = 1;
            stmt.setString(count++, dto.name());
            stmt.setString(count++, dto.address());
            stmt.setString(count++, dto.phone());
            stmt.setBoolean(count++, dto.isProfessional());
            stmt.setObject(count++, id);
            stmt.executeUpdate();
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        final String query = "DELETE FROM " + tableName + " WHERE id = ?::uuid";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}