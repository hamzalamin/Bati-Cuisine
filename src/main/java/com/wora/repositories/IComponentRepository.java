package com.wora.repositories;

import com.wora.models.dtos.ComponentDto;
import com.wora.models.entities.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IComponentRepository<DTO> {
    List<Component> findAll();
    Optional<Component> findByID(UUID id);
    void create(DTO dto);
    void update(DTO dto, UUID id);
    void delete(UUID id);
}
