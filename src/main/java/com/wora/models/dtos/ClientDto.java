package com.wora.models.dtos;

import java.util.UUID;

public record ClientDto(
        String name,
        String address,
        String phone,
        Boolean isProfessional
) {
}
