package com.wora.mapper.impl;

import com.wora.mapper.IComponentMapper;
import com.wora.models.entities.Client;
import com.wora.models.entities.Material;
import com.wora.models.entities.Project;
import com.wora.models.enums.ComponentType;
import com.wora.models.enums.ProjectStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MaterialMapper implements IComponentMapper<Material> {
    @Override
    public Material map(ResultSet rs) throws SQLException {
        return new Material(
                UUID.fromString(rs.getString("id")),
                rs.getDouble("tva"),
                ComponentType.valueOf(rs.getString("component_type")),
                new Project(
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
                ),
                UUID.fromString(rs.getString("id1")),
                rs.getDouble("unit_cost"),
                rs.getDouble("quantity"),
                rs.getDouble("transport_cost"),
                rs.getDouble("quality_coefficient")
        );
    }
}
