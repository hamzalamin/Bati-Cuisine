package com.wora.repositories;

import com.wora.models.dtos.ComponentDto;
import com.wora.models.entities.Component;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IComponentRepository<Entity, DTO> {
    List<Entity> findAll();
    Optional<Entity> findByID(UUID id);
    void create(DTO dto);
    void update(DTO dto, UUID id);
    void delete(UUID id);
    List<Entity> findAllByProjectId(UUID id);
}
