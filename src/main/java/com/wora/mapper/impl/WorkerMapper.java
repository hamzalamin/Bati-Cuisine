package com.wora.mapper.impl;

import com.wora.mapper.IComponentMapper;
import com.wora.models.entities.Material;
import com.wora.models.entities.Worker;
import com.wora.models.enums.ComponentType;

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
                UUID.fromString(rs.getString("project_id")),
                UUID.fromString(rs.getString("id")),
                rs.getDouble("hourly_rate"),
                rs.getTimestamp("work_Hours").toLocalDateTime(),
                rs.getDouble("worker_productivity")
        );
    }
}
