package com.wora.repositories;

import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientRepository {
    List<Client> findAll();
    Optional<Client> findById(UUID id);
    void create(ClientDto dto);
    Client update(ClientDto dto, UUID id);
    void delete(UUID id);
}
