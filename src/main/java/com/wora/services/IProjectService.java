package com.wora.services;

import com.wora.models.dtos.ProjectDto;
import com.wora.models.entities.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProjectService {
    List<Project> findAll()  throws SQLException;
    Optional<Project> findById(UUID id)  throws SQLException;
    void create(ProjectDto dto);
    void update(ProjectDto dto, UUID id);
    void delete(UUID id) throws SQLException;
}
