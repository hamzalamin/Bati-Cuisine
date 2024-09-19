package com.wora.models.dtos;

import com.wora.models.enums.ComponentType;

import java.util.UUID;

public class MaterialDto extends ComponentDto{
    private final Double unitCost;
    private final Double quantity;
    private final Double transportCost;
    private final Double qualityCoefficient;

    public MaterialDto(Double tva, ComponentType componentType, UUID projectId, Double unitCost, Double quantity, Double transportCost, Double qualityCoefficient) {
        super(tva, componentType, projectId);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }


    public Double getUnitCost() {
        return unitCost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public Double getQualityCoefficient() {
        return qualityCoefficient;
    }
}
