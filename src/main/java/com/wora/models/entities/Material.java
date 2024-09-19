package com.wora.models.entities;

import com.wora.models.enums.ComponentType;

import java.util.UUID;

public class Material extends Component {
    private UUID id;
    private Double unitCost;
    private Double quantity;
    private Double transportCost;
    private Double qualityCoefficient;

    public Material(UUID id, Double tva, ComponentType componentType, Project projectId, Double unitCost, Double quantity, Double transportCost, Double qualityCoefficient) {
        super(id, tva, componentType, projectId);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(Double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }

    public Double total() {
        return (unitCost * quantity * qualityCoefficient) + transportCost;
    }
}
