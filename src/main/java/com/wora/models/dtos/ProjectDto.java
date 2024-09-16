package com.wora.models.dtos;

import com.wora.models.entities.Client;
import com.wora.models.enums.ProjectStatus;

public record ProjectDto(
        String projectName,
        Double profitMargin,
        Double totalCost,
        ProjectStatus projectStatus,
        Client client_id
) {

}
