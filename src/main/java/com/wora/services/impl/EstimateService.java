package com.wora.services.impl;

import com.wora.models.dtos.EstimateDto;
import com.wora.models.entities.Estimate;
import com.wora.repositories.IEstimateRepository;
import com.wora.services.IEstimateService;

import java.util.List;
import java.util.UUID;

public class EstimateService  implements IEstimateService {
    final private IEstimateRepository repository;

    public EstimateService(IEstimateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Estimate> findAll() {
        return repository.findAll();
    }

    @Override
    public Estimate findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("estimate with id "+ id + " not found"));
    }

    @Override
    public void create(EstimateDto dto) {
        repository.create(dto);
    }

    @Override
    public void update(EstimateDto dto, UUID id) {
        repository.update(dto, id);
    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }

    @Override
    public List<Estimate> findClientEstimate(UUID id) {
        return repository.findClientEstimate(id);
    }
}
