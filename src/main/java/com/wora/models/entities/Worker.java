package com.wora.models.entities;

import com.wora.models.enums.ComponentType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Worker extends Component {
    private UUID id;
    private Double hourlyRate;
    private LocalDateTime workHours;
    private Double workerProductivity;

    public Worker(UUID id, Double tva, ComponentType componentType, UUID projectId, UUID id1, Double hourlyRate, LocalDateTime workHours, Double workerProductivity) {
        super(id, tva, componentType, projectId);
        this.id = id1;
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

    public LocalDateTime getWorkHours() {
        return workHours;
    }

    public void setWorkHours(LocalDateTime workHours) {
        this.workHours = workHours;
    }

    public Double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(Double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }
}
