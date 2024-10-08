package com.wora.models.dtos;

import com.wora.models.entities.Client;
import com.wora.models.enums.ProjectStatus;

import java.util.UUID;

public record ProjectDto(
        String projectName,
        Double profitMargin,
        Double totalCost,
        ProjectStatus projectStatus,
        UUID client_id,
        Double discount
) {

}
