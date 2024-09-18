package com.wora.services.impl;

import com.wora.models.dtos.MaterialDto;
import com.wora.models.entities.Project;
import com.wora.repositories.IComponentRepository;
import com.wora.services.IComponentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialService implements IComponentService<MaterialDto> {
    final IComponentRepository repository;

    public MaterialService(IComponentRepository repository) {
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
    public void create(MaterialDto materialDto) {
        repository.create(materialDto);
    }

    @Override
    public void update(MaterialDto materialDto, UUID id) {
        repository.update(materialDto, id);
    }


    @Override
    public void delete(UUID id) throws SQLException {
        repository.delete(id);
    }
}
