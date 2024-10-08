package com.wora.models.entities;

import com.wora.models.enums.ComponentType;

import java.util.UUID;

public class Worker extends Component {
    private UUID id;
    private Double hourlyRate;
    private Double workHours;
    private Double workerProductivity;

    public Worker(UUID id, Double tva, String name, ComponentType componentType, Project projectId, Double hourlyRate, Double workHours, Double workerProductivity) {
        super(id, tva, name, componentType, projectId);
        this.hourlyRate = hourlyRate;
        this.workHours = workHours;
        this.workerProductivity = workerProductivity;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }

    public Double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(Double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }

    @Override
    public Double total(){
        return (hourlyRate * workHours * workerProductivity);
    }
}
