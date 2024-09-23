package com.wora.repositories;

import com.wora.models.dtos.ProjectDto;
import com.wora.models.entities.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProjectRepository {
    List<Project> findAll();
    Optional<Project> findById(UUID id);
    UUID create(ProjectDto dto);
    void update(ProjectDto dto, UUID id);
    void delete(UUID id);
}
