package com.wora.models.dtos;

import com.wora.models.entities.Project;

import java.time.LocalDateTime;

public record EstimateDto(
    Double estimatedAmount,
    LocalDateTime issueDate,
    LocalDateTime validityDate,
    Boolean isAccept,
    Project projectId
) {
}
