package com.wora.repositories.impl;

import com.wora.config.JdbcConnection;
import com.wora.mapper.impl.MaterialMapper;
import com.wora.models.dtos.MaterialDto;
import com.wora.models.entities.Material;
import com.wora.repositories.IComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MaterialRepository extends ComponentRepository<Material,MaterialDto> implements IComponentRepository<MaterialDto> {
    private final Connection connection = JdbcConnection.getInstance().getConnection();
    public MaterialRepository(final MaterialMapper mapper) {
        super("materials", mapper);
    }

    @Override
    public void create(MaterialDto dto) {
        final String query = "INSERT INTO " +tableName+ " id, tva, component_type, project_id , unit_cost, quantity, transport_cost, quality_coefficient VALUES(?::uuid, ?, ?::component_type, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            int c = 1;
            stmt.setObject(c++, UUID.randomUUID());
            stmt.setDouble(c++, dto.getTva());
            stmt.setObject(c++, dto.getComponentType().toString().toUpperCase());
            stmt.setObject(c++, dto.getProjectId());
            stmt.setDouble(c++, dto.getUnitCost());
            stmt.setDouble(c++, dto.getQuantity());
            stmt.setDouble(c++, dto.getTransportCost());
            stmt.setDouble(c++, dto.getQualityCoefficient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MaterialDto dto, UUID id) {
        final String query = """
                UPDATE materials SET 
                tva = ?, component_type = ?::component_type , project_id = ?, unit_cost = ?, quantity = ?, transport_cost = ?, quality_coefficient = ?
                WHERE  id = ?::uuid
                """;
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            int c = 1;
            stmt.setDouble(c++, dto.getTva());
            stmt.setObject(c++, dto.getComponentType().toString().toUpperCase());
            stmt.setObject(c++, dto.getProjectId());
            stmt.setDouble(c++, dto.getUnitCost());
            stmt.setDouble(c++, dto.getQuantity());
            stmt.setDouble(c++, dto.getTransportCost());
            stmt.setDouble(c++, dto.getQualityCoefficient());
            stmt.setObject(c++, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
