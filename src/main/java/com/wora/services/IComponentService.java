package com.wora.services;

import com.wora.models.entities.Project;
import com.wora.models.entities.Worker;

import javax.swing.text.html.parser.Entity;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IComponentService<DTO , Entity> {
    List<Entity> findAll();
    Optional<Entity> findById(UUID id);
    void create(DTO dto);
    void update(DTO dto, UUID id);
    void delete(UUID id);
}
