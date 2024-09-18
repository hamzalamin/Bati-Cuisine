package com.wora.models.dtos;

import com.wora.models.enums.ComponentType;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.UUID;

public class WorkerDto extends ComponentDto {
//    private final UUID id;
    private final Double hourlyRate;
    private final Double workHour;
    private final Double workerProductivity;

    public WorkerDto(Double tva, ComponentType componentType, UUID projectId, Double hourlyRate, Double workHour, Double workerProductivity) {
        super(tva, componentType, projectId);
//        this.id = id;
        this.hourlyRate = hourlyRate;
        this.workHour = workHour;
        this.workerProductivity = workerProductivity;
    }

//    public UUID getId() {
//        return id;
//    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public Double getWorkHour() {
        return workHour;
    }

    public Double getWorkerProductivity() {
        return workerProductivity;
    }
}
