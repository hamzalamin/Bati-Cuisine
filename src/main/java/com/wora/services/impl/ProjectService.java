package com.wora.services.impl;

import com.wora.models.dtos.ProjectDto;
import com.wora.models.entities.Project;
import com.wora.repositories.IProjectRepository;
import com.wora.services.IProjectService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectService implements IProjectService {
    private final IProjectRepository repository;

    public ProjectService(IProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> findAll() throws SQLException {
        return repository.findAll();
    }

    @Override
    public Optional<Project> findById(UUID id) throws SQLException {
        return repository.findById(id);
    }

    @Override
    public void create(ProjectDto dto) {
        repository.create(dto);
    }

    @Override
    public void update(ProjectDto dto, UUID id) {
        repository.update(dto, id);
    }

    @Override
    public void delete(UUID id) throws SQLException {
        repository.delete(id);
    }
}
