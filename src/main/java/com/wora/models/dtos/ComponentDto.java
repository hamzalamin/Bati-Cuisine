package com.wora.models.dtos;

import com.wora.models.enums.ComponentType;

import java.util.UUID;

public class ComponentDto {
    private final Double tva;
    private final String name;
    private final ComponentType componentType;
    private final UUID projectId;


    public ComponentDto(Double tva, String name, ComponentType componentType, UUID projectId) {
        this.tva = tva;
        this.name = name;
        this.componentType = componentType;
        this.projectId = projectId;
    }

    public Double getTva() {
        return tva;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public String getName() {
        return name;
    }

    public UUID getProjectId() {
        return projectId;
    }


}
