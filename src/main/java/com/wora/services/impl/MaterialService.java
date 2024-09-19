package com.wora.services.impl;

import com.wora.models.dtos.MaterialDto;
import com.wora.models.entities.Material;
import com.wora.models.entities.Project;
import com.wora.models.entities.Worker;
import com.wora.repositories.IComponentRepository;
import com.wora.services.IComponentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialService implements IComponentService<MaterialDto, Material> {
    final IComponentRepository repository;

    public MaterialService(IComponentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Material> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Material> findById(UUID id){
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
    public void delete(UUID id){
        repository.delete(id);
    }
}
