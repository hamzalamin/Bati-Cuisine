package com.wora.services;

import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;
import com.wora.repositories.IClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientService implements IClientService{

    private final IClientRepository repository;

    public ClientService(IClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return repository.findById(id);
    }


    @Override
    public void create(ClientDto dto) {
        repository.create(dto);
    }

    @Override
    public void update(ClientDto dto, UUID id) {
        repository.update(dto, id);
    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }
}
