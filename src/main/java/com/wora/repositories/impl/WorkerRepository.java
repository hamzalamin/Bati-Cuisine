package com.wora.repositories.impl;

import com.wora.config.JdbcConnection;
import com.wora.mapper.impl.WorkerMapper;
import com.wora.models.dtos.WorkerDto;
import com.wora.models.entities.Worker;
import com.wora.repositories.IComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class WorkerRepository extends ComponentRepository<Worker,WorkerDto> implements IComponentRepository<Worker, WorkerDto> {
    private final Connection connection = JdbcConnection.getInstance().getConnection();

    public WorkerRepository(final WorkerMapper mapper) {
        super("workers", mapper);
    }

    @Override
    public void create(WorkerDto dto) {
        final String query = "INSERT INTO workers (id, tva, component_type, project_id , hourly_rate, worker_productivity, work_hours) " +
                " VALUES(?::uuid, ?, ?::component_type, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            int c = 1;
            stmt.setObject(c++, UUID.randomUUID());
            stmt.setDouble(c++, dto.getTva());
            stmt.setObject(c++, dto.getComponentType().toString().toUpperCase());
            stmt.setObject(c++, dto.getProjectId());
            stmt.setDouble(c++, dto.getHourlyRate());
            stmt.setDouble(c++, dto.getWorkerProductivity());
            stmt.setDouble(c++, dto.getWorkHour());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(WorkerDto dto, UUID id) {
        final String query = """
                UPDATE workers SET 
                tva = ?, component_type = ?::component_type , project_id = ?, hourly_rate = ?, worker_productivity = ? , work_hours = ?
                WHERE  id = ?::uuid
                """;
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            int c = 1;
            stmt.setDouble(c++, dto.getTva());
            stmt.setObject(c++, dto.getComponentType().toString().toUpperCase());
            stmt.setObject(c++, dto.getProjectId());
            stmt.setDouble(c++, dto.getHourlyRate());
            stmt.setDouble(c++, dto.getWorkerProductivity());
            stmt.setDouble(c++, dto.getWorkHour());
            stmt.setObject(c++, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
