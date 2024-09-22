package com.wora.models.dtos;

import com.wora.models.entities.Client;
import com.wora.models.enums.ProjectStatus;

import java.util.UUID;

public record ProjectDto(
        String projectName,
        Double profitMargin,
        Double totalCost,
        ProjectStatus projectStatus,
        Double projectTva,
        UUID client_id
) {

}
