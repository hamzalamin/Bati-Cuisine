package com.wora.services.impl;

import com.wora.models.dtos.WorkerDto;
import com.wora.models.entities.Project;
import com.wora.repositories.IComponentRepository;
import com.wora.services.IComponentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WorkerService implements IComponentService<WorkerDto> {
    final IComponentRepository repository;

    public WorkerService(IComponentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public Optional<Project> findById(UUID id) throws SQLException {
        return repository.findByID(id);
    }

    @Override
    public void create(WorkerDto workerDto) {
        repository.create(workerDto);
    }

    @Override
    public void update(WorkerDto workerDto, UUID id) {
        repository.update(workerDto, id);
    }

    @Override
    public void delete(UUID id) throws SQLException {
        repository.delete(id);
    }
}
