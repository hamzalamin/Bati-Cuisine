package com.wora.models.entities;

import com.wora.models.enums.ComponentType;

import java.util.UUID;

public abstract class Component {
    private UUID id;
    private Double tva;
    private String name;
    private ComponentType componentType;
    private Project projectId;

    public Component(UUID id, Double tva, String name ,ComponentType componentType, Project projectId) {
        this.id = id;
        this.tva = tva;
        this.name = name;
        this.componentType = componentType;
        this.projectId = projectId;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Double total();

    public Double totalWithTva() {
        return total() * (1 + tva / 100.0);
    }

}
