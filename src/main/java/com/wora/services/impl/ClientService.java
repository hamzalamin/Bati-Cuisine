package com.wora.services.impl;

import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;
import com.wora.repositories.IClientRepository;
import com.wora.services.IClientService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientService implements IClientService {

    private final IClientRepository repository;

    public ClientService(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("estimate with id "+ id + " not found"));
    }


    @Override
    public Client create(ClientDto dto) {
        return repository.create(dto);
    }

    @Override
    public void update(ClientDto dto, UUID id) {
        repository.update(dto, id);
    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }

    @Override
    public Client searchByName(String name) {
        return repository.searchByName(name);
    }
}
