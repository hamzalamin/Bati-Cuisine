package com.wora.repositories;

import com.wora.models.dtos.EstimateDto;
import com.wora.models.entities.Estimate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEstimateRepository {
    List<Estimate> findAll();
    Optional<Estimate> findById(UUID id);
    List<Estimate> findClientEstimate(UUID id);
    void create(EstimateDto dto);
    void update(EstimateDto dto, UUID id);
    void delete(UUID id);
}
