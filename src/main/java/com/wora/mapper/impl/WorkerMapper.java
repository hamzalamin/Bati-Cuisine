package com.wora.mapper.impl;

import com.wora.mapper.IComponentMapper;
import com.wora.models.entities.Client;
import com.wora.models.entities.Material;
import com.wora.models.entities.Project;
import com.wora.models.entities.Worker;
import com.wora.models.enums.ComponentType;
import com.wora.models.enums.ProjectStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class WorkerMapper implements IComponentMapper<Worker> {
    @Override
    public Worker map(ResultSet rs) throws SQLException {
        return new Worker(
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
                rs.getDouble("hourly_rate"),
                rs.getDouble("work_Hours"),
                rs.getDouble("worker_productivity")
        );
    }

    @Override
    public Worker mapProjectLess(ResultSet rs) throws SQLException{
        return new Worker(
                UUID.fromString(rs.getString("id")),
                rs.getDouble("tva"),
                ComponentType.valueOf(rs.getString("component_type")),
                null,
                rs.getDouble("hourly_rate"),
                rs.getDouble("work_Hours"),
                rs.getDouble("worker_productivity")
        );
    }
}
