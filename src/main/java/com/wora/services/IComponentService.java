package com.wora.services;

import com.wora.models.entities.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IComponentService<DTO> {
    List<Project> findAll()  throws SQLException;
    Optional<Project> findById(UUID id)  throws SQLException;
    void create(DTO dto);
    void update(DTO dto, UUID id);
    void delete(UUID id) throws SQLException;
}
