package com.wora.services;

import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientService {
    List<Client> findAll()  throws SQLException;
    Optional<Client> findById(UUID id)  throws SQLException;
    void create(ClientDto dto);
    Client update(ClientDto dto, UUID id);
    void delete(UUID id);
}
