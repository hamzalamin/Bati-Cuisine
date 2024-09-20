package com.wora.services;

import com.wora.models.dtos.EstimateDto;
import com.wora.models.entities.Estimate;

import java.util.List;
import java.util.UUID;

public interface IEstimateService {
    List<Estimate> findAll();
    Estimate findById(UUID id) ;
    void create(EstimateDto dto);
    void update(EstimateDto dto, UUID id);
    void delete(UUID id);
    List<Estimate> findClientEstimate(UUID id);
}
