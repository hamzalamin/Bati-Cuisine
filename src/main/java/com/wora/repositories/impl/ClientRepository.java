package com.wora.repositories.impl;

import com.wora.config.JdbcConnection;
import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;
import com.wora.models.entities.Estimate;
import com.wora.repositories.IClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository extends AbstractRepository<Client> implements IClientRepository {

    public ClientRepository() {
        super("clients");
    }

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
    public Client create(ClientDto dto) {
        final String query = "INSERT INTO " + tableName + " (id, name, address, phone, is_professional)" +
                " VALUES (?::uuid, ?, ?, ?, ?) RETURNING *";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int count = 1;
            stmt.setObject(count++, UUID.randomUUID());
            stmt.setString(count++, dto.name());
            stmt.setString(count++, dto.address());
            stmt.setString(count++, dto.phone());
            stmt.setBoolean(count++, dto.isProfessional());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                        (UUID) rs.getObject("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("is_professional")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
    public Client searchByName(String name) {
        final String query = "SELECT * FROM " + tableName + " WHERE name = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("is_professional")
                );
            } else {
                System.out.println("No client found with name: " + name);
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}