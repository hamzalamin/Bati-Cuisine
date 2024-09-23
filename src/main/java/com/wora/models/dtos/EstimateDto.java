package com.wora.models.dtos;

import com.wora.models.entities.Project;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstimateDto(
    Double estimatedAmount,
    LocalDateTime issueDate,
    LocalDateTime validityDate,
    Boolean isAccept,
    Double discount,
    UUID projectId
) {
}
